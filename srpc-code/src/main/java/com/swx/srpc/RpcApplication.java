package com.swx.srpc;

import com.swx.srpc.config.RegistryConfig;
import com.swx.srpc.config.RpcConfig;
import com.swx.srpc.constant.RpcConstant;
import com.swx.srpc.registry.Registry;
import com.swx.srpc.registry.RegistryFactory;
import com.swx.srpc.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RPC框架应用
 * 相当于holder 存放了项目全局用到的变量 双检锁单例实现
 */
public class RpcApplication {
    private static final Logger log = LoggerFactory.getLogger(RpcApplication.class);
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化 支持传入自定义配置
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init config ={}", newRpcConfig.toString());
        //注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init , config = {}", registryConfig);
        //创建并注册Shutdown Hook ,JVM退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    /**
     * 初始化
     */
    public static void init(){
         RpcConfig newRpcConfig;
         try{
             newRpcConfig = ConfigUtils.loaclConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
         }catch (Exception e){
             //配置加载失败 使用默认值
             newRpcConfig = new RpcConfig();
         }
         init(newRpcConfig);
    }

    /**
     * 获取配置
     */
    public static RpcConfig getRpcConfig() {
        if(rpcConfig == null){
            synchronized (RpcApplication.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
