package com.helix.datatrail.mapper.util;

import com.alibaba.fastjson.JSON;
import com.helix.datatrail.entity.DataTrailEntity;
import com.helix.datatrail.mapper.OpsHistoryMapper;
import com.helix.datatrail.mapper.DataTrailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

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

    public static <T> T queryLastHistoryDataTrail(Long opsObjectId,Long opsSearchId, Date opsDate,Class<T> objectType){
        OpsHistoryMapper opsHistoryMapper = MybatisUtil.getSqlSession().getMapper(OpsHistoryMapper.class);

        List<DataTrailEntity> opsHistory = opsHistoryMapper.getOpsHistoryById(opsObjectId,opsSearchId,opsDate,objectType.getSimpleName());

        if(!opsHistory.isEmpty()){
            return JSON.parseObject(opsHistory.get(0).getOpsObjectContent(),objectType);
        }else{
            return null;
        }
    }

    public static <T> T queryLastHistoryDataTrail2(String tableName,Long opsObjectId,Long opsSearchId, Date opsDate,Class<T> objectType){
        DataTrailMapper trailMapper = MybatisUtil.getSqlSession().getMapper(DataTrailMapper.class);

        List<DataTrailEntity> opsHistory = trailMapper.getOpsHistoryById(tableName,opsObjectId,opsSearchId,opsDate,objectType.getSimpleName());

        if(!opsHistory.isEmpty()){
            return JSON.parseObject(opsHistory.get(0).getOpsObjectContent(),objectType);
        }else{
            return null;
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
