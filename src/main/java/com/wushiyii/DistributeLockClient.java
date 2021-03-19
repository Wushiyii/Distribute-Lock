package com.wushiyii;

import com.wushiyii.exception.DistributeLockException;

/**
 * @Author: wgq
 * @Date: 2021/3/18 19:46
 */
public interface DistributeLockClient {

    DistributeLock getLock(String name) throws DistributeLockException;

}
