package com.helix.datatrail.mapper;

import com.helix.datatrail.entity.DataTrailEntity;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lijianyu
 * @date 2019/1/4 11:08
 */
public interface DataTrailMapper {

    /**
     * @deprecated
     * @param tableName
     * @param opsObjectId
     * @param opsObjectIdName
     * @return
     */
    @Select(" select * from ${tableName} where  ${opsObjectIdName} = #{opsObjectId} ")
    @ResultType(Map.class)
    Map getObjectTableRecord(@Param("tableName")String tableName,@Param("opsObjectId")Long opsObjectId,@Param("opsObjectIdName")String opsObjectIdName);

    /**
     * 获取数据操作流水记录
     * @param tableName
     * @param opsObjectId
     * @param opsTime
     * @return
     */
    @SelectProvider(type = DataTrailSqlBuilder.class, method = "buildGetOpsHistoryById")
    @Results(id = "_OpsHistory",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "ops_time",property = "opsTime"),
            @Result(column = "ops_object_name",property = "opsObjectName"),
            @Result(column = "ops_object_id",property = "opsObjectId"),
            @Result(column = "ops_object_content",property = "opsObjectContent"),
    })
    List<DataTrailEntity> getOpsHistoryById(@Param("tableName")String tableName,@Param("opsObjectName")String opsObjectName,@Param("opsObjectId")Long opsObjectId, @Param("opsTime")Date opsTime);

    /**
     * 记录数据操作流水
     * @param trailEntity
     * @param snapshotTableName
     * @return
     */
    @Insert(" INSERT INTO ${snapshotTableName}(ops_time,ops_event,ops_object_name,ops_object_id,ops_object_content) "
            + "  values(#{trailEntity.opsTime},#{trailEntity.opsEvent},#{trailEntity.opsObjectName},#{trailEntity.opsObjectId},#{trailEntity.opsObjectContent})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int insert(@Param("trailEntity")DataTrailEntity trailEntity,@Param("snapshotTableName")String snapshotTableName);
}
