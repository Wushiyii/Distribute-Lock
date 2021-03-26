package com.wushiyii;

import com.wushiyii.builder.EtcdLockBuilder;
import com.wushiyii.builder.RedissonLockBuilder;

/**
 * lock client factory
 * @Author: wgq
 * @Date: 2021/3/18 20:02
 */
public abstract class DistributeLockBuilder {

    public static EtcdLockBuilder etcd() {
        return EtcdLockBuilder.getInstance();
    }

    public static RedissonLockBuilder redisson() {
        return RedissonLockBuilder.getInstance();
    }

    public abstract DistributeLockClient build();

}
