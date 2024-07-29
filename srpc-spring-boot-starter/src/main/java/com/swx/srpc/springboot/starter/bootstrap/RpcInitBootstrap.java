package com.swx.srpc.springboot.starter.bootstrap;

import com.swx.srpc.RpcApplication;
import com.swx.srpc.config.RpcConfig;
import com.swx.srpc.server.tcp.VertxTcpServer;
import com.swx.srpc.springboot.starter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * RPC框架启动
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //获取EnableRPc注解的属性值
        boolean needServer = (boolean)importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName())
                .get("needServer");
        //RPC框架初始化
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        //启动服务器
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        }else {
            log.info("不启动 server");
        }
    }
}
