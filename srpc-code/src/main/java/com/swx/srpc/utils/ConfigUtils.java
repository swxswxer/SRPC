package com.swx.srpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * 配置工具类
 */
public class ConfigUtils {
    /**
     * 加载配置对象
     * @param tclass
     * @param prefix
     * @return
     * @param <T>
     */
    public static <T> T loaclConfig(Class<T> tclass, String prefix) {
        return loaclConfig(tclass,prefix,"");
    }

    /**
     * 加载配置对象 支持分区
     * @param tclass
     * @param prefix
     * @param environment
     * @return
     * @param <T>
     */
    public static <T> T loaclConfig(Class<T> tclass, String prefix, String environment) {
        StringBuffer configFileBuffer = new StringBuffer("application");
        if(StrUtil.isNotBlank(environment)){
            configFileBuffer.append("-").append(environment);
        }
        configFileBuffer.append(".properties");
        Props props = new Props(configFileBuffer.toString());
        return props.toBean(tclass,prefix);
    }
}
