package com.swx.srpc.registry;

import com.swx.srpc.config.RegistryConfig;
import com.swx.srpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心接口 方便以后扩展更多不同的注册中心
 */
public interface Registry {



    /**
     * 初始化
     * @param registryConfig
     */
    void init (RegistryConfig registryConfig);

    /**
     * 服务注册（服务端）
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register (ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 服务注销（服务端）
     * @param serviceMetaInfo
     */
    void unRegister (ServiceMetaInfo serviceMetaInfo) ;

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery (String serviceKey) ;

    /**
     * 服务销毁
     */
    void destroy ();

    /**
     * 心跳检测（服务端）
     */
    void heartBeat();

    /**
     * 监听 （消费端）
     */
    void watch(String serviceNodeKey);
}
