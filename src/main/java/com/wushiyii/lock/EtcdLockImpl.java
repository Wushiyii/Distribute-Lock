package com.wushiyii.lock;

import com.wushiyii.exception.DistributeLockException;
import com.wushiyii.helper.EtcdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EtcdLockImpl extends AbstractDistributeLock {

    private static final Logger logger = LoggerFactory.getLogger(EtcdLockImpl.class);

    public EtcdLockImpl(String key) {
        super(key);
    }

    @Override
    public boolean lock(int ttl) throws DistributeLockException {
        try {
            EtcdHelper.getInstance().put(super.key, super.value, ttl, "", false);
            return Boolean.TRUE;
        } catch (Exception e) {
            if (e instanceof DistributeLockException) {
                logger.error("Etcd lock occur error, key={}, ttl={}, value={}, code={}, message={}", key, ttl, super.value, ((DistributeLockException) e).getCode(), e.getMessage());
            } else {
                logger.error("Etcd lock occur error, key={}, ttl={}, value={}", super.key, ttl, super.value, e);
            }
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean unlock() throws DistributeLockException {
        try {
            EtcdHelper.getInstance().delete(super.key, super.value);
            return Boolean.TRUE;
        }  catch (Exception e) {
            if (e instanceof DistributeLockException) {
                logger.error("Etcd lock occur error, key={}, value={}, code={}, message={}", super.key, super.value, ((DistributeLockException) e).getCode(), e.getMessage());
            } else {
                logger.error("Etcd lock occur error, key={}, value={}", super.key, super.value, e);
            }
            return Boolean.FALSE;
        }
    }
}
