package com.swx.example.provider;

import com.swx.example.service.UserService;
import com.swx.srpc.RpcApplication;
import com.swx.srpc.registry.LocalRegistry;
import com.swx.srpc.server.HttpServer;
import com.swx.srpc.server.VertxHttpServer;

public class EsayProviderExample {
    public static void main(String[] args) {
        //RPC框架初始化
        RpcApplication.init();

        //注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
        //启动服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
