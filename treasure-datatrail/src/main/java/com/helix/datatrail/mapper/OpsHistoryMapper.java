package com.helix.datatrail.mapper;

import com.helix.datatrail.entity.DataTrailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author lijianyu
 * @date 2019/1/2 14:35
 */
public interface OpsHistoryMapper {

    List<DataTrailEntity> getOpsHistoryById(@Param("opsObjectId")Long opsObjectId, @Param("opsSearchId")Long opsSearchId,
                                            @Param("opsTime")Date opsTime, @Param("opsObjectName")String opsObjectName);

    int createOpsHistory(DataTrailEntity opsHistory);
}
