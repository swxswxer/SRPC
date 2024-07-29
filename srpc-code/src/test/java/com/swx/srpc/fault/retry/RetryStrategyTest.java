package com.swx.srpc.fault.retry;

import com.swx.srpc.model.RpcResponse;
import org.junit.Test;

/**
 * 重试策略测试
 */
public class RetryStrategyTest {
    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();
    @Test
    public void testRetry() {
        try{
            RpcResponse response = retryStrategy.doRetry(()->{
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(response);
        }catch (Exception e){
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
