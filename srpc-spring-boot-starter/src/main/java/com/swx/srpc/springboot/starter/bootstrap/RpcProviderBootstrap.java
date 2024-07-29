package com.swx.srpc.springboot.starter.bootstrap;


import com.swx.srpc.RpcApplication;
import com.swx.srpc.config.RegistryConfig;
import com.swx.srpc.config.RpcConfig;
import com.swx.srpc.model.ServiceMetaInfo;
import com.swx.srpc.registry.LocalRegistry;
import com.swx.srpc.registry.Registry;
import com.swx.srpc.registry.RegistryFactory;
import com.swx.srpc.springboot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Rpc服务提供者启动
 */
@Slf4j
public class RpcProviderBootstrap implements BeanPostProcessor {

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if (rpcService != null) {
            //需要注册服务
            //获取服务基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            //默认值处理
            if (interfaceClass == void.class) {
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();
            //注册服务
            //本地注册
            LocalRegistry.register(serviceName,beanClass);
            //全局配置
            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            }catch (Exception e){
                throw new RuntimeException(serviceName + "服务注册失败",e);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
