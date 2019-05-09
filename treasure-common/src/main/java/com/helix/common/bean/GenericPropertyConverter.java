package com.helix.common.bean;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 用于驼峰法命名和下划线(基于部分数据库命名规则)的属性转化 , i.e.userName<->user_name
 *
 * @author ljy
 * @date 2018/8/28 17:29
 */
public class GenericPropertyConverter {

    public static final char UNDERLINE_CHAR = '_';

    /**
     * 默认style camel  bean -> underline bean
     *
     * @param originObj
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T convertObject(Object originObj, Class<T> targetClass) {
        return baseConvertObject(originObj, targetClass, PropertyNameStyle.CAMEL, null, null);
    }

    public static <T> T convertObject(Object originObj, Class<T> targetClass, PropertyNameStyle originStyle) {
        return baseConvertObject(originObj, targetClass, originStyle, null, null);
    }

    /**
     * 默认style camel  bean -> underline bean
     *
     * @param originLst
     * @param targetClass
     * @param <T>
     * @param <F>
     * @return
     */
    public static <T, F> List<T> convertObject(List<F> originLst, Class<T> targetClass) {
        return convertObject(originLst, targetClass, PropertyNameStyle.CAMEL);
    }

    public static <T, F> List<T> convertObject(List<F> originLst, Class<T> targetClass, PropertyNameStyle originStyle) {
        if (Objects.isNull(originLst)) {
            return null;
        }
        List<T> result = new ArrayList<>(originLst.size());
        originLst.forEach(o -> result.add(convertObject(o, targetClass, originStyle)));
        return result;
    }

    /**
     * @param originObj                待转换的源对象，【不可为null】
     * @param targetClass              目标对象，【不可为null】FieldNamingPolicy
     * @param originStyle              源对象的property的命名风格，【不可为null】
     * @param customizePropertyNameMap 自定义转换属性，【可为null】
     * @param excludePropertyNameList  自定义的排除属性，【可为null】
     * @return
     */
    public static <T> T baseConvertObject(Object originObj, Class<T> targetClass, PropertyNameStyle originStyle,
                                          Map<String, String> customizePropertyNameMap, List<String> excludePropertyNameList) {
        if (Objects.isNull(originObj)) {
            return null;
        }

        T targetObj;
        try {
            targetObj = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(targetClass.getName()+"实例化失败:"+e.getMessage(),e);
        }

        Class<?> originClass = originObj.getClass();
        PropertyDescriptor[] originPds = BeanUtils.getPropertyDescriptors(originClass),
                targetPds = BeanUtils.getPropertyDescriptors(targetClass);
        Function<String, String> propertyConvertFunc = PropertyNameStyle.CAMEL.equals(originStyle)
                ? GenericPropertyConverter::camel2Underline
                : GenericPropertyConverter::underline2Camel;
        for (PropertyDescriptor originPd : originPds) {
            String propertyName = originPd.getName();
            if ("class".equals(propertyName)) {
                continue;
            }

            if (CollectionUtils.isNotEmpty(excludePropertyNameList) && excludePropertyNameList.contains(propertyName)) {
                continue;
            }

            String newPropertyName = StringUtils.EMPTY;
            if (MapUtils.isNotEmpty(customizePropertyNameMap)) {
                newPropertyName = customizePropertyNameMap.get(propertyName);
            }
            if (StringUtils.isBlank(newPropertyName)) {
                newPropertyName = propertyConvertFunc.apply(propertyName);
            }
            if (StringUtils.isBlank(newPropertyName)) {
                continue;
            }

            Class<?> originPropertyType = originPd.getPropertyType();
            for (PropertyDescriptor targetPd : targetPds) {
                if (newPropertyName.equals(targetPd.getName()) == false) {
                    continue;
                }

                Method originReadMethod = originPd.getReadMethod(),
                        targetWriteMethod = targetPd.getWriteMethod();
                if (originReadMethod != null && targetWriteMethod != null
                        && ClassUtils.isAssignable(targetWriteMethod.getParameterTypes()[0], originPropertyType)) {
                    try {
                        if (Modifier.isPublic(originReadMethod.getDeclaringClass().getModifiers()) == false) {
                            originReadMethod.setAccessible(true);
                        }
                        Object value = originReadMethod.invoke(originObj);
                        if (Modifier.isPublic(targetWriteMethod.getDeclaringClass().getModifiers()) == false) {
                            targetWriteMethod.setAccessible(true);
                        }
                        targetWriteMethod.invoke(targetObj, value);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        throw new RuntimeException("通用的风格属性名转换时出错", e);
                    }
                }
            }
        }
        return targetObj;
    }

    /**
     * 驼峰转下划线
     *
     * @param camelStr
     * @return
     */
    public static String camel2Underline(String camelStr) {
        if (StringUtils.isEmpty(camelStr)) {
            return StringUtils.EMPTY;
        }

        int len = camelStr.length();
        StringBuilder strb = new StringBuilder(len + len >> 1);
        for (int i = 0; i < len; i++) {
            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {
                strb.append(UNDERLINE_CHAR);
                strb.append(Character.toLowerCase(c));
            } else {
                strb.append(c);
            }
        }
        return strb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param underlineStr
     * @return
     */
    public static String underline2Camel(String underlineStr) {
        if (StringUtils.isEmpty(underlineStr)) {
            return StringUtils.EMPTY;
        }

        int len = underlineStr.length();
        StringBuilder strb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = underlineStr.charAt(i);
            if (c == UNDERLINE_CHAR && (++i) < len) {
                c = underlineStr.charAt(i);
                strb.append(Character.toUpperCase(c));
            } else {
                strb.append(c);
            }
        }
        return strb.toString();
    }

    public enum PropertyNameStyle {
        CAMEL,
        UNDERLINE,
    }
}