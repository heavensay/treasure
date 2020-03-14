package com.helix.datatrail.mapper;

import com.alibaba.fastjson.JSON;
import com.helix.datatrail.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Map;

/**
 * @author lijianyu
 * @date 2019/2/26 17:52
 */
public class DataTrailMapperTest{

    @Test
    public void getObjectTableRecord() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
//        Mapper test = sqlSession.getMapper(Mapper.class);
        DataTrailMapper dataTrailMapper = sqlSession.getMapper(DataTrailMapper.class);
//        System.out.println(test.countByExample(new Example()));
        Map map = dataTrailMapper.getObjectTableRecord("t_user",1L,"id");

        System.out.println(JSON.toJSONString(map));
        MybatisUtil.closeSession(sqlSession);
    }
}