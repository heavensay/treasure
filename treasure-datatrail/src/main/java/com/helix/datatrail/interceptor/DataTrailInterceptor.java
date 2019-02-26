package com.helix.datatrail.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.helix.datatrail.annotation.TrailTable;
import com.helix.datatrail.entity.DataTrailEntity;
import com.helix.datatrail.exception.DataTrailException;
import com.helix.datatrail.mapper.DataTrailMapper;
import com.helix.datatrail.mapper.util.ThreadLocalSqlSession;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Properties;

/**
 * @author lijianyu
 * @date 2018/12/29 17:48
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {
        MappedStatement.class, Object.class})})
public class DataTrailInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(DataTrailInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("===========进入拦截器");

        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        ParameterMap parameterMap = ms.getParameterMap();

        Object result = invocation.proceed();

        OpsEventTypeEnum opsEventType = OpsEventTypeEnum.UNKNOWN;
        switch (ms.getSqlCommandType()){
            case INSERT:
                opsEventType = OpsEventTypeEnum.INSERT;
                break;
            case UPDATE:
                opsEventType = OpsEventTypeEnum.UPDATE;
                break;
            case DELETE:
                opsEventType = OpsEventTypeEnum.DELETE;
                break;
            default:
                opsEventType = OpsEventTypeEnum.UNKNOWN;
        }

        if (needRecord(parameterMap.getType())) {
            createDataTrial(parameterMap.getType(),invocation.getArgs()[1], opsEventType);
        }

        return result;
    }

    private void queryBeforeUpdateData(Class classType,Object entity) throws InvocationTargetException, IllegalAccessException {
        TrailTable trailTable = getTrailTable();
        String objectTableName = trailTable.objectTableName();

        String objectIdName = trailTable.objectIdName();
        String opsObjectIdMethod = "get"+toFirstUpperCase(objectIdName);

        try {
            Long opsObjectId = (Long)entity.getClass().getMethod("get"+toFirstUpperCase(opsObjectIdMethod)).invoke(entity,null);
        } catch (NoSuchMethodException e) {
            throw new DataTrailException( opsObjectIdMethod + "方法不存在");
        }
    }

    private TrailTable getTrailTable(){
        return null;
    }

    private void createDataTrial(Class<?> classType, Object entity, OpsEventTypeEnum opsEventType) throws Throwable{
        TrailTable trailTable = classType.getAnnotation(TrailTable.class);
        String snapshotTableName = trailTable.snapshotTableName();
        String objectTableName = trailTable.objectTableName();
        String objectIdName = trailTable.objectIdName();
        String searchObjectName = trailTable.searchObjectName();
        String searchObjectIdName = trailTable.searchObjectIdName();

        String opsObjectName = entity.getClass().getSimpleName();

        Long opsObjectId = null;
        Long opsSearchId = null;
        if(objectIdName != null && !"".equals(objectIdName)){
            opsObjectId = (Long)entity.getClass().getMethod("get"+toFirstUpperCase(objectIdName)).invoke(entity,null);
        }
        if(searchObjectIdName != null && !"".equals(searchObjectIdName)){
            opsSearchId = (Long)entity.getClass().getMethod("get"+toFirstUpperCase(searchObjectIdName)).invoke(entity,null);
        }
        if(opsObjectId == null && opsSearchId == null){
            throw new DataTrailException("opsObjectId，opsSearchId不能同时为空");
        }

        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String opsObjectContent = JSON.toJSONString(entity,config);
        DataTrailEntity dataTrailEntity = new DataTrailEntity();
        dataTrailEntity.setOpsObjectContent(opsObjectContent);
        dataTrailEntity.setOpsObjectId(opsObjectId);
        dataTrailEntity.setOpsObjectName(opsObjectName);
        dataTrailEntity.setOpsSearchObjectId(opsSearchId);
        dataTrailEntity.setOpsSearchObjectName(null);
        dataTrailEntity.setOpsTime(new Date());
        dataTrailEntity.setOpsEvent(opsEventType.getCode());

        DataTrailMapper opsHistoryMapper = ThreadLocalSqlSession.get().getMapper(DataTrailMapper.class);
        opsHistoryMapper.insert(dataTrailEntity);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private boolean needRecord(Class tableEntity) {
        TrailTable trailTable = (TrailTable) tableEntity.getAnnotation(TrailTable.class);
        return trailTable != null;
    }

    /**
     * 首字母转为大写
     * @param str
     * @return
     */
    private String toFirstUpperCase(String str){
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
