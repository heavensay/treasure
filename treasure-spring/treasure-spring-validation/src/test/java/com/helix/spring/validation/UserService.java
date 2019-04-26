package com.helix.spring.validation;

import com.helix.spring.validation.group.ValidationUserAdd;
import com.helix.spring.validation.group.ValidationUserUpdate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author ljy
 * @date 2019/4/18 11:32
 */
public class UserService implements IUserService{

    public void addUser(@Validated(value = ValidationUserAdd.class) @NotNull User user, @NotNull(message = "用户名不能为空1111",groups = ValidationUserUpdate.class)String username){
//    public void addUser(@Valid User user, @NotNull(message = "用户名不能为空") String username){
        System.out.println("add user success");
    }

    @Validated(value = {ValidationUserUpdate.class})
    public void createUser(@Validated @NotNull User user, @NotNull(message = "用户名不能为空for create user")String username){
        System.out.println("add user success");
    }

    public void modifyUser(@Valid User user){
        System.out.println("modify user success");
    }
}
