package com.swx.example.conumer;

import com.swx.example.model.User;
import com.swx.example.service.UserService;
import com.swx.srpc.bootstrap.ConsumerBootstrap;
import com.swx.srpc.proxy.ServiceProxyFactory;


/**
 * 简易服务事例
 */
public class ConsumerExample {

    public static void main(String[] args) {
        //初始化
        ConsumerBootstrap.init();
        //获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        User user = new User();
        user.setName("swx");

        //调用
        System.out.println("开始调用");
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        System.out.println("结束调用");

//        long number = userService.getNumber();
//        System.out.println(number);
    }
}
