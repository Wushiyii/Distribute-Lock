package com.wushiyii.builder;

import com.wushiyii.DistributeLockBuilder;
import com.wushiyii.DistributeLockClient;
import com.wushiyii.client.EtcdLockClient;
import com.wushiyii.exception.DistributeLockException;
import com.wushiyii.helper.EtcdHelper;

/**
 * @Author: wgq
 * @Date: 2021/3/18 20:04
 */
public class EtcdLockBuilder extends DistributeLockBuilder {

    private EtcdLockBuilder() {};
    private static EtcdLockBuilder etcdLockBuilder;

    public static EtcdLockBuilder getInstance() {
        if (null == etcdLockBuilder) {
            synchronized (EtcdLockBuilder.class) {
                if (null == etcdLockBuilder) {
                    etcdLockBuilder = new EtcdLockBuilder();
                }
            }
        }
        return etcdLockBuilder;
    }

    public EtcdLockBuilder urls(String... urls) {
        EtcdHelper.getInstance().setUrls(urls);
        return etcdLockBuilder;
    }

    @Override
    public DistributeLockClient build() {
        return EtcdLockClient.getInstance();
    }
}
