package com.helix.datatrail.util;

import com.alibaba.fastjson.JSON;
import com.helix.datatrail.annotation.TrailTable;
import com.helix.datatrail.entity.DataTrailEntity;
import com.helix.datatrail.exception.DataTrailException;
import com.helix.datatrail.mapper.DataTrailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据操作工具类
 * @author lijianyu
 * @date 2019/1/3 9:58
 */
public class DataTrailDBUtils {

    private static Logger logger = LoggerFactory.getLogger(DataTrailDBUtils.class);

    /**
     * 获取opsDate之前最新的一条记录
     * @param <T>
     * @param opsObjectId
     * @param opsDate
     * @param objectType
     * @return
     */
    public static <T> T queryNewestSnapshotByTime(Class<T> objectType, Long opsObjectId, Date opsDate){
        List<T> entities = listSnapshotByTime(objectType,opsObjectId,opsDate);

        if(entities.size()>0){
            return entities.get(0);
        }else{
            return null;
        }
    }

    /**
     * 获取opsDate之前最新的一条记录JSON字符串
     * @param objectType
     * @param opsObjectId
     * @param opsDate
     * @return
     */
    public static String queryJsonSnapshotByTime( Class objectType, Long opsObjectId, Date opsDate){
        List<String> contentJson = listJsonSnapshotByTime(objectType, opsObjectId, opsDate);
        if(contentJson.size()>0){
            return contentJson.get(0);
        }else{
            return null;
        }
    }

    /**
     * 返回当前时间节点的数据记录
     * @param opsObjectId
     * @param opsDate
     * @param objectType
     * @param <T>
     * @return
     */
    public static <T> List<T> listSnapshotByTime(Class<T> objectType, Long opsObjectId, Date opsDate){
        List<String> contentJsons = listJsonSnapshotByTime(objectType,opsObjectId,opsDate);

        return contentJsons.stream().map(e->{
            //数据转为实体
            return JSON.parseObject(e,objectType);
        }).collect(Collectors.toList());
    }

    /**
     * 返回当前时间节点的数据记录JSON字符串
     * @param objectType
     * @param opsObjectId
     * @param opsDate
     * @return
     */
    public static List<String> listJsonSnapshotByTime(Class<?> objectType,Long opsObjectId, Date opsDate){
        DataTrailMapper trailMapper = DataTrailSqlSessionManager.obtainSqlSession().getMapper(DataTrailMapper.class);

        String tableName = objectType.getSimpleName();
        String opsObjectName = objectType.getSimpleName();
        TrailTable trailTable = objectType.getAnnotation(TrailTable.class);

        if(trailTable == null){
            throw new DataTrailException(objectType.getName()+"中没有找到TrailTable，不支持历史数据查询");
        }else{
            if(trailTable.snapshotTableName()!=null && !"".equals(trailTable.snapshotTableName())){
                tableName = trailTable.snapshotTableName();
            }
        }

        List<DataTrailEntity> dataTrailEntities = trailMapper.getOpsHistoryById(tableName,opsObjectName,opsObjectId,opsDate);

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
}
