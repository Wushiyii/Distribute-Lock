package com.wushiyii.client;

import com.wushiyii.DistributeLock;
import com.wushiyii.DistributeLockClient;
import com.wushiyii.exception.DistributeLockException;
import com.wushiyii.lock.RedissonLockImpl;

/**
 * @Author: wgq
 * @Date: 2021/3/24 13:55
 */
public class RedissonLockClient implements DistributeLockClient {

    private static RedissonLockClient instance;

    public static RedissonLockClient getInstance() {
        if (null == instance) {
            synchronized (RedissonLockClient.class) {
                if (null == instance) {
                    instance = new RedissonLockClient();
                }
            }
        }
        return instance;
    }

    @Override
    public DistributeLock getLock(String name) throws DistributeLockException {
        return new RedissonLockImpl(name);
    }
}
