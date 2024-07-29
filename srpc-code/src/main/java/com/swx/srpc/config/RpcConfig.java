package com.swx.srpc.config;

import com.swx.srpc.fault.retry.RetryStrategyKeys;
import com.swx.srpc.fault.tolerant.TolerantStrategyKeys;
import com.swx.srpc.loadbalancer.LoadBalancerKeys;
import com.swx.srpc.serializer.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {
    //名称
    private String name = "yu-rpc";
    //版本号
    private String version = "1.0";
    //服务器主机
    private String serverHost = "loaclhost";
    //服务器端口号
    private Integer serverPort = 8080;
    //模拟调用
    private boolean mock = false;
    //序列化器
    private String serializer = SerializerKeys.JDK;
    //注册中心
    private RegistryConfig registryConfig = new RegistryConfig();
    //负载均衡器
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;
    //重试策略
    private String retryStrategy = RetryStrategyKeys.FIXED_INTERVAL;
    //容错策略
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;

}
