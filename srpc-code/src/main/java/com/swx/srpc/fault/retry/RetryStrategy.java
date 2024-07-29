package com.swx.srpc.fault.retry;

import com.swx.srpc.model.RpcResponse;

import java.util.concurrent.Callable;

public interface RetryStrategy {
    /**
     * 重试
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;


}
