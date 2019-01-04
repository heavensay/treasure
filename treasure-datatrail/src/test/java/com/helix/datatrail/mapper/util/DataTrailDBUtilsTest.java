package com.helix.datatrail.mapper.util;

import com.helix.datatrail.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lijianyu
 * @date 2019/1/3 17:47
 */
public class DataTrailDBUtilsTest {

    @Test
    public void queryHistoryDataTrail() {
        User user = DataTrailDBUtils.queryLastHistoryDataTrail(1L,null,new Date(), User.class);
        Assert.assertNotNull(user);
    }

    @Test
    public void queryHistoryDataTrail2q() {
        User user = DataTrailDBUtils.queryLastHistoryDataTrail2("t_ops_history",1L,null,new Date(), User.class);
        Assert.assertNotNull(user);
    }
}