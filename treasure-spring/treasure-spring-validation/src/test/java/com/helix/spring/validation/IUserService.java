package com.helix.spring.validation;

import org.springframework.validation.annotation.Validated;

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

