package com.wushiyii.helper;

import com.wushiyii.exception.DistributeLockException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;


/**
 * @Author: wgq
 * @Date: 2021/3/24 12:53
 */
public class RedissonHelper {

    private static final Logger log = LoggerFactory.getLogger(RedissonHelper.class);

    private String[] urls;
    private RedissonClient redissonClient;

    private static volatile RedissonHelper instance;

    public static RedissonHelper getInstance() {
        if (null == instance) {
            synchronized (RedissonHelper.class) {
                if (null == instance) {
                    instance = new RedissonHelper();
                }
            }
        }
        return instance;
    }

    // TODO finish config single/cluster/master&slave
    public synchronized void build() {
        if (Objects.nonNull(redissonClient)) {
            throw new DistributeLockException("redissonClient all ready initialized");
        }
        Config redissonConfig = new Config();

        if (null == this.urls || this.urls.length == 0) {
            throw new DistributeLockException("Redisson urls not set, could not build client");
        }

        log.info("Redisson client initialing..., urls={}", Arrays.toString(this.urls));
        redissonConfig.useClusterServers().addNodeAddress(this.urls);

        redissonClient = Redisson.create(redissonConfig);

    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public RedissonClient getRedissonClient() {
        return this.redissonClient;
    }



}
