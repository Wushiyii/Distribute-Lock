package com.wushiyii.builder;

import com.wushiyii.DistributeLockBuilder;
import com.wushiyii.DistributeLockClient;
import com.wushiyii.client.EtcdLockClient;
import com.wushiyii.exception.DistributeLockException;

/**
 * @Author: wgq
 * @Date: 2021/3/18 20:04
 */
public class EtcdLockBuilder extends DistributeLockBuilder {

    private String[] urls;

    private static EtcdLockBuilder etcdLockBuilder;

    public static EtcdLockBuilder getInstance() {
        if (null == etcdLockBuilder) {
            synchronized (EtcdLockBuilder.class) {
                if (null == etcdLockBuilder) {
                    etcdLockBuilder = new EtcdLockBuilder();
                }
            }
        }
        return etcdLockBuilder;
    }

    public EtcdLockBuilder urls(String[] urls) {
        etcdLockBuilder.setUrls(urls);
        return etcdLockBuilder;
    }

    @Override
    public DistributeLockClient build() {
        EtcdLockBuilder instance = getInstance();
        if (null == instance.getUrls() || instance.getUrls().length == 0) {
            throw new DistributeLockException("Etcd urls not set, could not build client");
        }

        // TODO build etcd lock helper

        return EtcdLockClient.getInstance();
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
