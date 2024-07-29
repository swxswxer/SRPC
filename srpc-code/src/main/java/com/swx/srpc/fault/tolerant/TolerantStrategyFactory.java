package com.swx.srpc.fault.tolerant;

import com.swx.srpc.spi.SpiLoader;

public class TolerantStrategyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class);
    }
    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
