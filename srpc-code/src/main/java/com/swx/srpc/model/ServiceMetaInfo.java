package com.swx.srpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 服务元信息
 */
@Data
public class ServiceMetaInfo {
    //服务名称
    private String serviceName;
    //服务版本号
    private String serviceVersion="1.0";
    //服务域名
    private String serviceHost;
    //服务端口号
    private Integer servicePort;
    //服务分组（暂时未实现）
    private String serviceGroup = "default";

    /**
     * 获取服务名
     * @return
     */
    public String getServiceKey() {
        //后续可扩展服务分组
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost,servicePort);
    }

    public String getServiceAddress(){
        if(StrUtil.contains(serviceHost,"http")){
            return String.format("http://%s:%s",serviceHost,servicePort);
        }
        return String.format("%s:%s",serviceHost,servicePort);
    }


}
