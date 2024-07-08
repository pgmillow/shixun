package com.penguin.service.impl;

import com.penguin.mapper.UserMapper;
import com.penguin.pojo.User;
import com.penguin.service.UserService;
import com.penguin.utils.Md5Util;
import com.penguin.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserName(String userName) {

        return  userMapper.findByUserName(userName);
    }


    @Override
    public void register(String username, String password) {
        //加密
        String md5string = Md5Util.getMD5String(password);
        userMapper.add(username,md5string);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateavatar(String avatarUrl) {

        Map <String,Object> map= ThreadLocalUtil.get();
        String username  = (String) map.get("username");
        if (username != null) {
            userMapper.updateavatar(avatarUrl, username);
        } else {
            // 处理 id 为 null 的情况，例如抛出异常或记录日志
            throw new IllegalArgumentException("ID is null, cannot update avatar.");
        }
    }

    @Override
    public void updatePwd(String newPwd) {
        Map <String,Object> map= ThreadLocalUtil.get();

        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd) , id );
    }


}
