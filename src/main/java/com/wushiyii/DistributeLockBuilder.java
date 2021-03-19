package com.wushiyii;

import com.wushiyii.builder.EtcdLockBuilder;

/**
 * lock client factory
 * @Author: wgq
 * @Date: 2021/3/18 20:02
 */
public abstract class DistributeLockBuilder {

    EtcdLockBuilder etcd() {
        return EtcdLockBuilder.getInstance();
    }

    public abstract DistributeLockClient build();

}
