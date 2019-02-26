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

    @Select(" select * from ${tableName} where  ${opsObjectIdName} = #{opsObjectId} ")
    @ResultType(Map.class)
    Map getObjectTableRecord(@Param("tableName")String tableName,@Param("opsObjectId")Long opsObjectId,@Param("opsObjectIdName")String opsObjectIdName);

    @SelectProvider(type = DataTrailSqlBuilder.class, method = "buildGetOpsHistoryById")
    @Results(id = "_OpsHistory",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "ops_time",property = "opsTime"),
            @Result(column = "ops_search_id",property = "opsSearchId"),
            @Result(column = "ops_object_name",property = "opsObjectName"),
            @Result(column = "ops_object_id",property = "opsObjectId"),
            @Result(column = "ops_object_content",property = "opsObjectContent"),
    })
    List<DataTrailEntity> getOpsHistoryById(@Param("tableName")String tableName,@Param("opsObjectId")Long opsObjectId, @Param("opsSearchId")Long opsSearchId,
                                       @Param("opsTime")Date opsTime, @Param("opsObjectName")String opsObjectName);
//    List<DataTrailEntity> getOpsHistoryById(@Param("tableName")String tableName, DataTrailEntity dataTrailEntity);

//    @Insert("insert into table2 (name) values(#{name})")
//    List<DataTrailEntity> insert(@Param("tableName")String tableName, @Param("opsObjectId")Long opsObjectId, @Param("opsSearchId")Long opsSearchId,
//                                 @Param("opsTime")Date opsTime, @Param("opsObjectName")String opsObjectName);

    @Insert(" INSERT INTO t_ops_history(ops_time,ops_event,ops_object_name,ops_object_id,ops_object_content,ops_search_object_name,ops_search_object_id) "
            + "  values(#{opsTime},#{opsEvent},#{opsObjectName},#{opsObjectId},#{opsObjectContent},#{opsSearchObjectName},#{opsSearchObjectId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int insert(DataTrailEntity trailEntity);
}
