package com.wushiyii.lock;

import com.wushiyii.DistributeLock;
import com.wushiyii.exception.DistributeLockException;
import com.wushiyii.helper.EtcdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class EtcdLock implements DistributeLock {

    private static final Logger logger = LoggerFactory.getLogger(EtcdLock.class);

    private String key;
    private String value;

    public EtcdLock(String key) {
        this.key = key;
        this.value = UUID.randomUUID().toString();
    }

    @Override
    public boolean lock(int ttl) throws DistributeLockException {
        try {
            EtcdHelper.getInstance().put(key, value, "", false);
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error("Etcd lock occur error, key={}, value={}", key, value, e);
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean lock(int ttl, int sleepTime, int retryTime) throws DistributeLockException {
        int count = 0;
        while (count < retryTime) {
            if (!lock(ttl)) {
                count++;
                try {
                    Thread.sleep(sleepTime * 1000L);
                } catch (InterruptedException e) {
                    logger.error("Etcd lock put occur error, key={}, value={}", key, value, e);
                }
            } else {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean unlock() throws DistributeLockException {
        try {
            EtcdHelper.getInstance().delete(key, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error("Etcd unlock occur error, key={}, value={}", key, value, e);
            return Boolean.FALSE;
        }
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
