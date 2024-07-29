package com.swx.srpc.bootstrap;

import com.swx.srpc.RpcApplication;
import com.swx.srpc.config.RegistryConfig;
import com.swx.srpc.config.RpcConfig;
import com.swx.srpc.model.ServiceMetaInfo;
import com.swx.srpc.model.ServiceRegisterInfo;
import com.swx.srpc.registry.LocalRegistry;
import com.swx.srpc.registry.Registry;
import com.swx.srpc.registry.RegistryFactory;
import com.swx.srpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 *服务提供者初始化
 */
public class ProviderBootstrap {
    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList){
        //RPC框架初始化 (配置和注册中心)
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName,serviceRegisterInfo.getImplClass());
            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try{
                registry.register(serviceMetaInfo);
            }catch (Exception e){
                throw  new RuntimeException(serviceName + "服务注册失败",e);
            }
        }
        //启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
