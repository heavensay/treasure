package com.helix.dict.introspect;

import com.helix.dict.annotation.Dict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对bean进行内省，获取需要进行字典转换的元数据
 *
 * @author ljy
 * @date 2019/7/11 20:21
 */
public class DictBeanIntrospector {

    private static Logger logger = LoggerFactory.getLogger(DictBeanIntrospector.class);

    private static Class[] primitiveWrappers = new Class[]{
            Boolean.class,
            Byte.class,
            Character.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Void.class
    };

    private static Map<Class, List<DictMetadata>> cache = new ConcurrentHashMap<>();

    private final static List arrayDictMetadatas = new ArrayList();

    static {
        arrayDictMetadatas.add(new ArrayDictMetadata());
    }

    /**
     * 按照bean命名规范来进行解析；field的名字和类型要跟bean规范的方法对应起来；
     * @param beanClass
     * @return not be null
     */
    public static List<DictMetadata> acquireDictMetadata(Class beanClass) {
        if(isBasicJavaClass(beanClass)){
            return Collections.EMPTY_LIST;
        }

        if (cache.containsKey(beanClass)) {
            return cache.get(beanClass);
        }

        //数组class
        if(beanClass.isArray()){
            return arrayDictMetadatas;
        }

        //是实体类
        List<Field>  fields = getAllField(beanClass);


        removeDuplicateField(fields);

        BeanInfo beanInfo = null;
        List<DictMetadata> result = new ArrayList();
        try {
            beanInfo = Introspector.getBeanInfo(beanClass,Object.class);

        } catch (IntrospectionException e) {
            throw new RuntimeException("class:"+beanClass.getName()+",内省失败",e);
        }

        for (Field field : fields) {
            String fieldName = field.getName();

            Dict dict = field.getAnnotation(Dict.class);
            if (dict != null && !isBasicJavaClass(field.getType())){
                //暂时不支持非基础类型字典值的翻译
                logger.info("class:{},属性:{}，type:{}，不支持的字典翻译类型，忽略此属性的字典翻译", beanClass, fieldName, field.getType());
                continue;
            }

            if (isBasicJavaClass(field.getType())) {
                if (dict == null) {
                    continue;
                }

                Class type = dict.type();
                String category = dict.category();
                String code = dict.code();
                String valueColumn = dict.valueColumn();

                Method valueLabelWriteMethod = null;
                Method valueColumnReadMethod = null;

                int readAndWriteMatch = 0;
                for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                    if (fieldName.equals(propertyDescriptor.getName())) {
                        Class writeMethodParater = propertyDescriptor.getWriteMethod().getParameters()[0].getType();
                        if(writeMethodParater == field.getType()){
                            //保证类型一致
                            valueLabelWriteMethod = propertyDescriptor.getWriteMethod();
                            readAndWriteMatch++;
                        }else{
                            logger.info("class:{},属性:{}，属性类型:{},写入方法参数类型:{}," +
                                    "类型与写入方法的参数类型不匹配，忽略此属性的字典翻译",
                                    beanClass, fieldName,field.getType().getName(),writeMethodParater.getName());
                        }
                    }
                    if (valueColumn.equals(propertyDescriptor.getName())) {
                        valueColumnReadMethod = propertyDescriptor.getReadMethod();
                        readAndWriteMatch++;
                    }

                    if (readAndWriteMatch >= 2) {
                        break;
                    }
                }

                if (valueLabelWriteMethod == null) {
                    logger.info("class:{},属性:{}写入方法不存在，忽略此属性的字典翻译", beanClass, fieldName);
                    continue;
                }
                if (valueColumnReadMethod == null) {
                    logger.info("class:{},属性:{},对应的valueColumn:{}读方法不存在，忽略此属性的字典翻译", beanClass, fieldName,valueColumn);
                    continue;
                }

                PrimitiveDictMetadata dictMetadata = new PrimitiveDictMetadata(fieldName, type, category, code, valueColumn, valueColumnReadMethod, valueLabelWriteMethod);
                dictMetadata.setValueColumnReadMethod(valueColumnReadMethod);
                dictMetadata.setValueLabelWriteMethod(valueLabelWriteMethod);

                result.add(dictMetadata);
            } else {
                //获取对象类型字段的元数据
                Method objectFieldReadMethod = null;

                for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                    if (fieldName.equals(propertyDescriptor.getName())) {
                        objectFieldReadMethod = propertyDescriptor.getReadMethod();
                        break;
                    }
                }

                if (objectFieldReadMethod == null) {
                    continue;
                }

                ObjectDictMetadata objectDictMetadata = new ObjectDictMetadata(fieldName, objectFieldReadMethod);
                result.add(objectDictMetadata);
            }
        }

        if(result.size() == 0) result = Collections.EMPTY_LIST;

        cache.put(beanClass,result);
        return result;
    }


    /**
     * 判断是否是java的基本类型(包括：8种基础类型以及对应包装类，void类型，String类型)
     *
     * @param clazz
     * @return
     */
    private static boolean isBasicJavaClass(Class clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }

        if (String.class.isAssignableFrom(clazz)) {
            return true;
        }

        return isWrapPrimitive(clazz);
    }

    /**
     * 判断是否为8种基础类型的包装类和Void类型
     *
     * @param clazz
     * @return
     */
    private static boolean isWrapPrimitive(Class clazz) {
        for (Class primitiveWrapper : primitiveWrappers) {
            if (primitiveWrapper == clazz) {
                return true;
            }
        }
        return false;
    }

    private static List<Field> getAllField(Class clazz){
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            for (Field field : declaredFields) {
                allFields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

    /**
     * 移除field名字；保留子类的field
     */
    private static void removeDuplicateField(List<Field> fields){
        for (int i=fields.size()-1;i>=0;i--) {
            Field currentField = fields.get(i);
            for(int j = i-1;j>=0;j--){
                Field previousField = fields.get(j);
                if(currentField.getName().equals(previousField.getName())){
                    fields.remove(i);
                    break;
                }
            }
        }
    }
}
