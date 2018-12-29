package com.helix.datatrail.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * @author lijianyu
 * @date 2018/12/29 17:48
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {
        MappedStatement.class, Object.class})})
public class DataTrailInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("=============:");
        JSONUtils.toJSONString(invocation);
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
