package com.helix.datatrail.mapper.util;

import com.alibaba.fastjson.JSON;
import com.helix.datatrail.entity.DataTrailEntity;
import com.helix.datatrail.mapper.OpsHistoryMapper;
import com.helix.datatrail.mapper.DataTrailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijianyu
 * @date 2019/1/3 9:58
 */
public class DataTrailDBUtils {

    Logger logger = LoggerFactory.getLogger(DataTrailDBUtils.class);

//    OpsHistoryMapper opsHistoryMapper = ThreadLocalSqlSession.get().getMapper(OpsHistoryMapper.class);
//
//    public static int insert(){
//        opsHistoryMapper.createOpsHistory(opsHistory);
//    }

    public static <T> T queryLastHistoryDataTrailByMapperXML(Long opsObjectId, Long opsSearchId, Date opsDate, Class<T> objectType){
        OpsHistoryMapper opsHistoryMapper = MybatisUtil.getSqlSession().getMapper(OpsHistoryMapper.class);

        List<DataTrailEntity> opsHistory = opsHistoryMapper.getOpsHistoryById(opsObjectId,opsSearchId,opsDate,objectType.getSimpleName());

        if(!opsHistory.isEmpty()){
            return JSON.parseObject(opsHistory.get(0).getOpsObjectContent(),objectType);
        }else{
            return null;
        }
    }

    public static String queryJsonSnapshotByTime(String tableName, Long opsObjectId, Long opsSearchId, Date opsDate, Class objectType){
        List<String> contentJson = listJsonSnapshotByTime(tableName, opsObjectId, opsSearchId, opsDate, objectType);
        if(contentJson.size()>0){
            return contentJson.get(0);
        }else{
            return null;
        }
    }

    public static List<String> listJsonSnapshotByTime(String tableName, Long opsObjectId, Long opsSearchId, Date opsDate, Class objectType){
        DataTrailMapper trailMapper = MybatisUtil.getSqlSession().getMapper(DataTrailMapper.class);

        List<DataTrailEntity> dataTrailEntities = trailMapper.getOpsHistoryById(tableName,opsObjectId,opsSearchId,opsDate,objectType.getSimpleName());

        if(!dataTrailEntities.isEmpty()){
            //快照发生时间之前时间最大(最近)的记录
            DataTrailEntity maxTimeEntity = filterMaxOpsTime(dataTrailEntities);
            return dataTrailEntities.stream().filter(e->{
                //过滤时间相同的数据
                return e.getOpsTime().compareTo(maxTimeEntity.getOpsTime()) == 0;
            }).map(e->{
                //数据转为实体
                return e.getOpsObjectContent();
            }).collect(Collectors.toList());

        }else{
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * 获取最新的一条记录
     * @param <T>
     * @param tableName
     * @param opsObjectId
     * @param opsSearchId
     * @param opsDate
     * @param objectType
     * @return
     */
    public static <T> T querySnapshotByTime(String tableName, Long opsObjectId, Long opsSearchId, Date opsDate, Class<T> objectType){
        List<T> entities = listSnapshotByTime(tableName,opsObjectId,opsSearchId,opsDate,objectType);

        if(entities.size()>0){
            return entities.get(0);
        }else{
            return null;
        }
    }

    /**
     * 返回当前时间节点的数据记录
     * @param tableName
     * @param opsObjectId
     * @param opsSearchId
     * @param opsDate
     * @param objectType
     * @param <T>
     * @return
     */
    public static <T> List<T> listSnapshotByTime(String tableName, Long opsObjectId, Long opsSearchId, Date opsDate, Class<T> objectType){
        List<String> contentJsons = listJsonSnapshotByTime(tableName,opsObjectId,opsSearchId,opsDate,objectType);

        return contentJsons.stream().map(e->{
            //数据转为实体
            return JSON.parseObject(e,objectType);
        }).collect(Collectors.toList());
    }

    /**
     * 获取opsTime时间最大的一条记录
     * @param entities
     * @return
     */
    private static DataTrailEntity filterMaxOpsTime(List<DataTrailEntity> entities){
        DataTrailEntity maxTimeEntity = entities.stream().
                sorted(Comparator.comparing(DataTrailEntity::getOpsTime)).
                findFirst().orElse(null);

        return maxTimeEntity;
    }

    /**
     * 返回当前时间节点的数据记录
     * @param tableName
     * @param opsObjectId
     * @param opsSearchId
     * @param opsDate
     * @param objectType
     * @param <T>
     * @return
     */
    public static <T> List<T> listSnapshotByTime22(String tableName, Long opsObjectId, Long opsSearchId, Date opsDate, Class<T> objectType){
        DataTrailMapper trailMapper = MybatisUtil.getSqlSession().getMapper(DataTrailMapper.class);

        List<DataTrailEntity> opsHistory = trailMapper.getOpsHistoryById(tableName,opsObjectId,opsSearchId,opsDate,objectType.getSimpleName());

        if(!opsHistory.isEmpty()){
            return opsHistory.stream().map(e->{
                //json转为实体类
                return JSON.parseObject(opsHistory.get(0).getOpsObjectContent(),objectType);
            }).collect(Collectors.toList());
        }else{
            return Collections.EMPTY_LIST;
        }
    }

//    public static <T> T queryLastHistoryDataTrail2(String tableName, Long opsObjectId, Long opsSearchId, Date opsTime, Class<T> objectType){
//        DataTrailMapper trailMapper = MybatisUtil.getSqlSession().getMapper(DataTrailMapper.class);
//
//        DataTrailEntity searchOpsHistory = new DataTrailEntity();
//        searchOpsHistory.setOpsSearchId(opsSearchId);
//        searchOpsHistory.setOpsObjectId(opsObjectId);
//        searchOpsHistory.setOpsTime(opsTime);
//        searchOpsHistory.setOpsObjectName(objectType.getSimpleName());
//
//        List<DataTrailEntity> opsHistory = trailMapper.getOpsHistoryById(tableName,searchOpsHistory);
//
//        if(!opsHistory.isEmpty()){
//            return JSON.parseObject(opsHistory.get(0).getOpsObjectContent(),objectType);
//        }else{
//            return null;
//        }
//    }
}
