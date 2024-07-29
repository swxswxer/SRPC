package com.yupi.examplespringbootprovider;

import com.swx.example.model.User;
import com.swx.example.service.UserService;
import com.swx.srpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类

 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
