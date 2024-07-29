package com.swx.example.service;

import com.swx.example.model.User;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 获取一个数字
     * @return
     */
    default short getNumber(){
        return 1;
    }
}
