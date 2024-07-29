package com.swx.srpc.springboot.starter.annotation;

import com.swx.srpc.constant.RpcConstant;
import com.swx.srpc.fault.retry.RetryStrategyKeys;
import com.swx.srpc.fault.tolerant.TolerantStrategyKeys;
import com.swx.srpc.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务消费者注解 （用于注入服务）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface RpcService {
    /**
     * 服务接口类
     */
    Class<?> interfaceClass() default void.class;
    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     */
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    String retryStrategy() default RetryStrategyKeys.NO;
    /**
     * 容错策略
     */
    String tolerantStrategy() default TolerantStrategyKeys.FAIL_BACK;
    /**
     * 模拟调用
     */
    boolean mock() default false;
}
