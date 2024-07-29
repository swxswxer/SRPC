package com.swx.example.provider;

import com.swx.example.model.User;
import com.swx.example.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名"+user.getName());
        return user;
    }
}
