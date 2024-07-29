package com.swx.example.conumer;

import com.swx.example.model.User;
import com.swx.example.service.UserService;
import com.swx.srpc.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者事例
 */
public class EsayConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("swx");
        //调用
        User newUser = userService.getUser(user);
        if(newUser != null) {
            System.out.println(newUser.getName());
        }else {
            System.out.println("user == null");
        }

    }
}
