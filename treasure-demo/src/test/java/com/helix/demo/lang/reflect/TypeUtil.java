package com.helix.demo.lang.reflect;

import java.lang.reflect.*;

/**
 * @author ljy
 * @date 2019/6/13 14:29
 */
public class TypeUtil {

    /**
     * 获取type对应最深层次的class对象 (e.g.  Bean1<Bean2<Bean3>>  renturn Bean3.class)
     *
     * @param type
     * @return
     */
    public static Class getLastGenericClass(Type type) {
        if (type instanceof TypeVariable) {
            Type[] bounds = ((TypeVariable) type).getBounds();
            return getLastGenericClass(bounds[0]);
        } else if (type instanceof ParameterizedType) {
            Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();//Map<K,V>这种就会有多个type
//            return getLastGenericClass(actualTypes[0]);
            return getLastGenericClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof WildcardType) {
            Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            Type tmp = lowerBounds.length > 0 ? lowerBounds[0] : upperBounds[0];
            return getLastGenericClass(tmp);
        } else if (type instanceof GenericArrayType) {
            return getLastGenericClass(((GenericArrayType) type).getGenericComponentType());
        } else if (type instanceof Class) {
            return (Class) type;
        }

        return Object.class;
    }
}
