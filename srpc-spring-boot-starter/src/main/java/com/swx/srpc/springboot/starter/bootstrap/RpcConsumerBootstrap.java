package com.swx.srpc.springboot.starter.bootstrap;

import com.swx.srpc.proxy.ServiceProxyFactory;
import com.swx.srpc.springboot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * Rpc服务消费者启动
 */
@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        //遍历对象所有属性
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                //未属性生成代理对象
                Class<?> interfaceClass = rpcReference.interfaceClass();
                if (interfaceClass == void.class) {
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);
                Object proxyObject = ServiceProxyFactory.getProxy(interfaceClass);
                try{
                    field.set(bean,proxyObject);
                    field.setAccessible(false);
                }catch (IllegalAccessException e){
                    throw new RuntimeException("为字段注入代理对象失败",e);
                }

            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

}
