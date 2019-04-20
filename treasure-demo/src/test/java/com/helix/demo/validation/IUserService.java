package com.helix.demo.validation;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author ljy
 * @date 2019/4/18 11:32
 */
@Validated
public interface IUserService {

    void addUser( User user,String username);
//    void addUser(@Valid User user, @NotNull(message = "用户名不能为空2222") String username);

    void modifyUser(User user);
}

