package com.helix.common.bean.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijianyu
 * @date 2021/4/20 下午3:39
 */
@Mapper
public interface MapStructUtil {

    public MapStructUtil INSTANCE = Mappers.getMapper(MapStructUtil.class);

//    @Mapping(source = "nums",target = "nums",qualifiedByName="convertNums")
    public Bean2 toBean2(Bean1 bean1);

//    @Named("convertNums")
//    List<Long> nums2(List<Long> nums);
}
