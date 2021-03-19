package com.wushiyii;

import com.wushiyii.exception.DistributeLockException;

/**
 * @Author: wgq
 * @Date: 2021/3/18 19:49
 */
public interface DistributeLock {

    boolean lock(int ttl) throws DistributeLockException;

    boolean lock(int ttl, int sleepTime, int retryTime) throws DistributeLockException;

    boolean unlock() throws DistributeLockException;

}
