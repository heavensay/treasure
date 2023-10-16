package com.helix.common.bean.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianyu
 * @date 2023/10/15 17:40
 */
@Mapper
public interface BeanMapper{

    BeanMapper INSTANCE = Mappers.getMapper(BeanMapper.class);

     Bean2 source2Target(Bean1 bean1);
//
//    Bean2 target2Source(Bean2 bean1);
//
//    @Named("source2TargetList")
    List<Bean2> source2TargetList(List<Bean1> bean1);
}
