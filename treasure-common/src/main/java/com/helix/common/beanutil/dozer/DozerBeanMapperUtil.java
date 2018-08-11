package com.helix.common.beanutil.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lijianyu
 * @date 2018/7/31 14:16
 */
public class DozerBeanMapperUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DozerBeanMapperUtil.class);

    private static Mapper mapper = null;

    static {
        List<String> filePaths = new ArrayList<String>();
        filePaths.add("dozerBeanMapping.xml");
        try{
            mapper = new DozerBeanMapper(filePaths);
            System.out.println("===");
        }catch (MappingException me){
            LOGGER.warn(me.getMessage(),me);
            mapper = new DozerBeanMapper();
        }
    }

    public static <T> T map(Object source, Class<T> cls) {
        if(source == null){
            return null;
        }
        return mapper.map(source, cls);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<T>();
        for (Object source : sourceList){
            T destinationObject = map(source, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }
}
