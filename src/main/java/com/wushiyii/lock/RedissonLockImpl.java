package com.wushiyii.lock;

import com.wushiyii.exception.DistributeLockException;
import com.wushiyii.helper.RedissonHelper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wgq
 * @Date: 2021/3/24 13:56
 */
public class RedissonLockImpl extends AbstractDistributeLock {

    private static final Logger logger = LoggerFactory.getLogger(RedissonLockImpl.class);

    public RedissonLockImpl(String key) {
        super(key);
    }

    @Override
    public boolean lock(int ttl) throws DistributeLockException {
        RedissonClient redissonClient = RedissonHelper.getInstance().getRedissonClient();
        try {
            RLock lock = redissonClient.getLock(super.key);
            lock.lock(ttl, TimeUnit.SECONDS);
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error("Redisson lock occur error key={}, ttl={}", super.key, ttl, e);
            return Boolean.FALSE;
        }

    }

    @Override
    public boolean unlock() throws DistributeLockException {
        RedissonClient redissonClient = RedissonHelper.getInstance().getRedissonClient();
        RLock lock = redissonClient.getLock(super.key);
        lock.unlock();
        return Boolean.TRUE;
    }
}
