package com.wushiyii.client;

import com.wushiyii.DistributeLock;
import com.wushiyii.DistributeLockClient;
import com.wushiyii.exception.DistributeLockException;
import com.wushiyii.lock.EtcdLock;

/**
 * @Author: wgq
 * @Date: 2021/3/18 20:30
 */
public class EtcdLockClient implements DistributeLockClient {

    private static EtcdLockClient instance;

    public static EtcdLockClient getInstance() {
        if (null == instance) {
            synchronized (EtcdLockClient.class) {
                if (null == instance) {
                    instance = new EtcdLockClient();
                }
            }
        }
        return instance;
    }


    @Override
    public DistributeLock getLock(String name) throws DistributeLockException {
        return new EtcdLock(name);
    }
}
