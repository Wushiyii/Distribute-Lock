package com.wushiyii.builder;

import com.wushiyii.DistributeLockBuilder;
import com.wushiyii.DistributeLockClient;
import com.wushiyii.client.RedissonLockClient;
import com.wushiyii.exception.DistributeLockException;
import com.wushiyii.helper.EtcdHelper;
import com.wushiyii.helper.RedissonHelper;

/**
 * @Author: wgq
 * @Date: 2021/3/24 12:51
 */
public class RedissonLockBuilder extends DistributeLockBuilder {

    private RedissonLockBuilder(){}

    private static RedissonLockBuilder redissonLockBuilder;

    public static RedissonLockBuilder getInstance() {
        if (null == redissonLockBuilder) {
            synchronized (RedissonLockBuilder.class) {
                if (null == redissonLockBuilder) {
                    redissonLockBuilder = new RedissonLockBuilder();
                }
            }
        }
        return redissonLockBuilder;
    }

    public RedissonLockBuilder urls(String... urls) {
        RedissonHelper.getInstance().setUrls(urls);
        return redissonLockBuilder;
    }


    @Override
    public DistributeLockClient build() {
        RedissonHelper.getInstance().build();
        return RedissonLockClient.getInstance();
    }
}
