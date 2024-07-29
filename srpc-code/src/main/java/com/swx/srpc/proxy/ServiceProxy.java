package com.swx.srpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.swx.srpc.RpcApplication;
import com.swx.srpc.config.RpcConfig;
import com.swx.srpc.constant.RpcConstant;
import com.swx.srpc.fault.retry.RetryStrategy;
import com.swx.srpc.fault.retry.RetryStrategyFactory;
import com.swx.srpc.fault.tolerant.TolerantStrategy;
import com.swx.srpc.fault.tolerant.TolerantStrategyFactory;
import com.swx.srpc.loadbalancer.LoadBalancer;
import com.swx.srpc.loadbalancer.LoadBalancerFactory;
import com.swx.srpc.model.RpcRequest;
import com.swx.srpc.model.RpcResponse;
import com.swx.srpc.model.ServiceMetaInfo;
import com.swx.srpc.registry.Registry;
import com.swx.srpc.registry.RegistryFactory;
import com.swx.srpc.serializer.Serializer;
import com.swx.srpc.serializer.SerializerFactory;
import com.swx.srpc.server.tcp.VertxTcpClient;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理（JDK 动态代理）
 *
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
//        Serializer serializer = new JdkSerializer();
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            //从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            //负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getLoadBalancer(rpcConfig.getLoadBalancer());
            //将调用方法名（请求路径） 作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
            System.err.println("调用 " + selectServiceMetaInfo.getServiceName() + " 服务  地址：" + selectServiceMetaInfo.getServiceAddress());
            //rpc请求
            //使用重试机制
            RpcResponse rpcResponse;
            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() ->
                        VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo)
                );
            }catch (Exception e){
                //容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null,e);
            }
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException("调用失败");
        }
    }
}
