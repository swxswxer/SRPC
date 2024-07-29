package com.swx.srpc.fault.tolerant;

import com.swx.srpc.model.RpcResponse;

import java.util.Map;

public class FailBackTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //TODO 未扩展
        return null;
    }
}
