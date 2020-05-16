package com.helix.datatrail.mapper.user;

import com.helix.datatrail.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @author lijianyu
 * @date 2018/12/29 16:44
 */
@Mapper
public interface UserMapper {

    User getUserById(@Param("id") Long id);

    @Insert("INSERT INTO user(name,age,create_date) values(#{name},#{age},#{createDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createUser(User user);
}
