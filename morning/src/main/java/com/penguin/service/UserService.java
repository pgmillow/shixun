package com.penguin.service;

import com.penguin.pojo.User;

public interface UserService {

    User findUserName(String userName);

    //根据用户名查询用户
    //注册
    void register(String username, String password);
    //  更新
    void update(User user);

    void updateavatar(String updateavatar);

    void updatePwd(String newPwd);
}
