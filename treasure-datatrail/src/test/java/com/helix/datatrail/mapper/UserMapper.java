package com.helix.datatrail.mapper;

import com.helix.datatrail.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author lijianyu
 * @date 2018/12/29 16:44
 */
public interface UserMapper {

    User getUserById(@Param("id") Long id);

    int createUser(User user);
}
