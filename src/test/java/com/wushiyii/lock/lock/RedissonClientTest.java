package com.wushiyii.lock.lock;

import com.wushiyii.DistributeLock;
import com.wushiyii.DistributeLockBuilder;
import com.wushiyii.DistributeLockClient;
import com.wushiyii.lock.BaseTest;
import org.junit.Test;

/**
 * @Author: wgq
 * @Date: 2021/3/26 10:04
 */
public class RedissonClientTest extends BaseTest {

    @Test
    public void stLock() throws InterruptedException {
        DistributeLockClient client = DistributeLockBuilder.redisson().urls("redis://127.0.0.1:6379", "redis://127.0.0.1:6379").build();
        DistributeLock lock1 = client.getLock("lock11");
        print(lock1.lock(5));

        print(lock1.lock(5));

        DistributeLock lock2 = client.getLock("lock22");
        print(lock2.lock(5));

        Thread.sleep(6000);
        print(lock2.lock(5));

    }
}
