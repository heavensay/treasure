package com.helix.datatrail.mapper.util;

import com.helix.datatrail.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * @author lijianyu
 * @date 2019/1/3 17:47
 */
public class DataTrailDBUtilsTest {

    @Test
    public void queryHistoryDataTrail2q() {
        User user = DataTrailDBUtils.queryNewestSnapshotByTime( User.class,1L,new Date());
        Assert.assertNotNull(user);
    }
}