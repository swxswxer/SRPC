package com.swx.srpc.fault.tolerant;

import com.swx.srpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 快速失败 - 容错策略 （立刻通知外层调用方）
 */
public class FailFastTolerantStrategy implements TolerantStrategy {
    private static final Logger log = LoggerFactory.getLogger(FailFastTolerantStrategy.class);

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("容错机制启动");
        throw new RuntimeException("服务报错",e);
    }
}
