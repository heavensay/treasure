package com.helix.common.beanutil.dozer;

import org.dozer.CustomConverter;

/**
 * @author lijianyu
 * @date 2018/8/6 16:19
 */
public class DozerCustomConverter implements CustomConverter {

    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        return null;
    }

//    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
////        return GenericPropertyConverter.convertObject(sourceFieldValue,destinationClass);
//        return null;
//    }
}
