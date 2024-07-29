package com.swx.srpc.loadbalancer;

import com.swx.srpc.spi.SpiLoader;

/**
 * 负载均衡器工厂 (工厂模式 用于获取负载均衡器对象)
 */
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }
    /**
     * 默认负载均衡器 轮询
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取事例
     * @param key
     * @return
     */
    public static LoadBalancer getLoadBalancer(String key) {
        return SpiLoader.getInstance(LoadBalancer.class,key);
    }


}
