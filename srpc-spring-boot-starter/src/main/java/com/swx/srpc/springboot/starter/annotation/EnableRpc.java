package com.swx.srpc.springboot.starter.annotation;

import com.swx.srpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.swx.srpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.swx.srpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用rpc注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {
    /**
     * 需要启动server
     */
    boolean needServer() default true;
}
