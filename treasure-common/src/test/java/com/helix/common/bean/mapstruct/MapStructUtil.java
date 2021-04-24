package com.helix.common.bean.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author lijianyu
 * @date 2021/4/20 下午3:39
 */
@Mapper
public interface MapStructUtil {

    public MapStructUtil INSTANCE = Mappers.getMapper(MapStructUtil.class);

    public Bean2 toBean2(Bean1 bean1);
}
