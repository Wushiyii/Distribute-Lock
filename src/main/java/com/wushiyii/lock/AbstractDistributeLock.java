package com.wushiyii.lock;

import com.wushiyii.DistributeLock;
import com.wushiyii.exception.DistributeLockException;

import java.util.UUID;

/**
 * @Author: wgq
 * @Date: 2021/3/24 14:15
 */
public abstract class AbstractDistributeLock implements DistributeLock {

    protected String key;
    protected String value;

    public AbstractDistributeLock(String key) {
        this.key = key;
        this.value = UUID.randomUUID().toString();
    }

    @Override
    public abstract boolean lock(int ttl) throws DistributeLockException;

    @Override
    public boolean lock(int ttl, int sleepTime, int retryTime) throws DistributeLockException {
        int count = 0;
        while (count < retryTime) {
            if (!lock(ttl)) {
                count++;
                try {
                    Thread.sleep(sleepTime * 1000L);
                } catch (InterruptedException ignored) { }
            } else {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public abstract boolean unlock() throws DistributeLockException;
}
