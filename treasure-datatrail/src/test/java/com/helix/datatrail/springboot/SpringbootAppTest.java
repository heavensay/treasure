package com.helix.datatrail.springboot;

import com.helix.datatrail.entity.User;
import com.helix.datatrail.util.DataTrailDBUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootAppTest {
    @Autowired
    public WebApplicationContext wac;

    public MockMvc mockMvc = null;

    @Test
    public void createUser() throws Exception {
        DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(wac);

        mockMvc = defaultMockMvcBuilder.build();
        mockMvc.perform(post("/createUser")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void query() throws Exception {
        User user = DataTrailDBUtils.queryNewestSnapshotByTime(User.class, 3L,new Date());

        Assert.assertNotNull(user);
    }
}
