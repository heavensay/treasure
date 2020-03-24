package com.helix.datatrail.mapper.user;

import com.helix.datatrail.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author lijianyu
 * @date 2018/12/29 16:44
 */
@Mapper
public interface UserMapper {

    User getUserById(@Param("id") Long id);

    @Insert("        INSERT INTO user(name,age,create_date)\n" +
            "          values(#{name},#{age},#{createDate})")
    int createUser(User user);
}
