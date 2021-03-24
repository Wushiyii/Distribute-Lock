package com.wushiyii;

import com.wushiyii.exception.DistributeLockException;

/**
 * @Author: wgq
 * @Date: 2021/3/18 19:49
 */
public interface DistributeLock {

    boolean lock(int ttl) throws DistributeLockException;

    default boolean lock(int ttl, int sleepTime, int retryTime) throws DistributeLockException {
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

    boolean unlock() throws DistributeLockException;

}
