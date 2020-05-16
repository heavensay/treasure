package com.helix.datatrail.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.helix.datatrail.annotation.TrailTable;
import com.helix.datatrail.entity.DataTrailEntity;
import com.helix.datatrail.exception.DataTrailException;
import com.helix.datatrail.mapper.DataTrailMapper;
import com.helix.datatrail.util.DataTrailSqlSessionManager;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
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
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        ParameterMap parameterMap = ms.getParameterMap();

        Object result = invocation.proceed();

        //更新成功之后才进行快照数据记录
        if (result != null && result instanceof Integer && (Integer)result>0) {
            if (needRecord(parameterMap.getType())) {
                logger.debug("开始记录快照数据");
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
                createDataTrial(parameterMap.getType(),invocation.getArgs()[1], opsEventType);
            }
        }else{
            logger.warn("result:{} unknown,不能记录快照数据",result);
        }
        return result;
    }

    private void queryBeforeUpdateData(Class classType,Object entity) throws InvocationTargetException, IllegalAccessException {
        TrailTable trailTable = getTrailTable();

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
        String objectIdName = trailTable.objectIdName();

        String opsObjectName = entity.getClass().getSimpleName();

        Long opsObjectId = null;
        if(objectIdName != null && !"".equals(objectIdName)){
            opsObjectId = (Long)entity.getClass().getMethod("get"+toFirstUpperCase(objectIdName)).invoke(entity,null);
        }
        if(opsObjectId == null){
            throw new DataTrailException("opsObjectId value can not be null");
        }

        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String opsObjectContent = JSON.toJSONString(entity,config);
        DataTrailEntity dataTrailEntity = new DataTrailEntity();
        dataTrailEntity.setOpsObjectContent(opsObjectContent);
        dataTrailEntity.setOpsObjectId(opsObjectId);
        dataTrailEntity.setOpsObjectName(opsObjectName);
        dataTrailEntity.setOpsTime(new Date());
//        dataTrailEntity.setOpsEvent(opsEventType.getCode()+"ddddddppppppppccccccccjjjjjjjjjkkkkkkkk");
        dataTrailEntity.setOpsEvent(opsEventType.getCode());
        dataTrailEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        dataTrailEntity.setModifyTime(new Timestamp(System.currentTimeMillis()));

        //事务由外面逻辑来决定提交或回滚

        //事务都由项目来统一提交或回滚；
        SqlSession sqlSession = DataTrailSqlSessionManager.obtainSqlSession();
        logger.debug("DataTrail connection of SqlSession aucoCommit:{},isReadOnly:{}",sqlSession.getConnection().getAutoCommit(),sqlSession.getConnection().isReadOnly());
        DataTrailMapper opsHistoryMapper = sqlSession.getMapper(DataTrailMapper.class);
        opsHistoryMapper.insert(dataTrailEntity,snapshotTableName);
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
