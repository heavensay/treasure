package com.helix.dict;

import com.helix.dict.annotation.DictConfiguration;
import com.helix.dict.introspect.*;
import com.helix.dict.source.DefaultDictSourceManage;
import com.helix.dict.source.EnumDictSource;
import com.helix.dict.source.IDictSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 字典数据的便捷操作工具类
 * @author ljy
 * @date 2019/7/4 13:53
 */
public final class SysDictManager{

    private static Logger logger = LoggerFactory.getLogger(SysDictManager.class);

    private static DefaultDictSourceManage dictSourceManage = new DefaultDictSourceManage();

    static{
        dictSourceManage.register(EnumDictSource.INSTANCE);
    }

    /**
     * 对实例进行字典映射
     * @param instance
     */
    public static void mapping(Object instance){
        if(instance == null) return;

        //先进行isntance的字典翻译
        List<DictMetadata> dictMetadata = DictBeanIntrospector.acquireDictMetadata(instance.getClass());
        dictMetadata.stream().forEach(dm -> {
            try {
                if (dm instanceof PrimitiveDictMetadata) {
                    PrimitiveDictMetadata pdm = (PrimitiveDictMetadata) dm;

                    Object value = pdm.getValueColumnReadMethod().invoke(instance, null);
                    Object valueLabel = get(pdm.getType(), pdm.getCategory(), pdm.getCode(), value);

                    Class writeMethodParameterClass = pdm.getValueLabelWriteMethod().getParameters()[0].getType();

                    if(valueLabel != null && !writeMethodParameterClass.isAssignableFrom(valueLabel.getClass())){
                        logger.info("class:{},field:{},字典值类型{}与写方法的入参类型{}不匹配",instance.getClass(),pdm.getFieldName(),
                                valueLabel.getClass().getName(),writeMethodParameterClass.getName());
                    }

                    if(valueLabel != null && writeMethodParameterClass.isAssignableFrom(valueLabel.getClass())){
                        pdm.getValueLabelWriteMethod().invoke(instance, valueLabel);
                    }
                } else if (dm instanceof ObjectDictMetadata) {
                    ObjectDictMetadata objectDictMetadata = (ObjectDictMetadata) dm;
                    Object bean = objectDictMetadata.getObjectFieldReadMethod().invoke(instance, null);
                    mapping(bean);
                }else if(dm instanceof ArrayDictMetadata){
                    ArrayDictMetadata arrayDictMetadata = (ArrayDictMetadata) dm;
                    int length = Array.getLength(instance);
                    for (int i=0;i<length;i++){
                        mapping(Array.get(instance,i));
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("字典翻译失败", e);
            }
        });

        //Collection中的元素进行字典翻译
        if(instance instanceof Collection){
            Object[] beans = ((Collection) instance).toArray();
            for (Object b : beans) {
                mapping(b);
            }
        }else if(instance instanceof Map){
            //Map中的元素进行字典翻译
            Object[] beans = ((Map) instance).values().toArray();
            for (Object b : beans) {
                mapping(b);
            }
        }
    }

    /**
     * 便捷的查询方法
     * @param type
     * @param value
     * @return
     */
    public static Object queryByTypeAndValue(Class type, Object value) {
        DictKey dictKey = new DictKey(type, DictConfiguration.DEFAULT_CATEGORY,DictConfiguration.DEFAULT_CODE,value);
        return get(dictKey);
    }

    public static Object get(Class type, String code, Object value) {
        DictKey dictKey = new DictKey(type, DictConfiguration.DEFAULT_CATEGORY, code, value);
        return get(dictKey);
    }

    public static Object get(Class type, String category, String code, Object value) {
        DictKey dictKey = new DictKey(type, category, code, value);
        return get(dictKey);
    }

    /**
     * 获取字典值
     * @param dictKey
     * @return
     */
    public static Object get(DictKey dictKey) {
        for (IDictSource dictSource:dictSourceManage.getAllDictSource()) {
            Object value = dictSource.get(dictKey);
            if(value!=null){
                return value;
            }
        }
        return null;
    }

    public static boolean containKey(DictKey dictKey) {
        for (IDictSource dictSource:dictSourceManage.getAllDictSource()) {
            boolean isExist = dictSource.containKey(dictKey);
            if(isExist){
                return isExist;
            }
        }
        return false;
    }

    /**
     * 注册数据源
     * @param dictSource
     */
    public static void registerDictSource(IDictSource dictSource){
        dictSourceManage.register(dictSource);
    }

}

