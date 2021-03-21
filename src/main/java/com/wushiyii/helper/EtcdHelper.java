package com.wushiyii.helper;

/**
 * ETCD communicator
 * @Author: wgq
 * @Date: 2021/3/19 18:53
 */
public class EtcdHelper {

    private String[] urls;

    private static volatile EtcdHelper instance;

    public static EtcdHelper getInstance() {
        if (null == instance) {
            synchronized (EtcdHelper.class) {
                if (null == instance) {
                    instance = new EtcdHelper();
                }
            }
        }
        return instance;
    }

    public void put(String key, String value, String prevValue, boolean prevExist) {
        // TODO  send http
    }

    public void delete(String key, String prevValue) {
        // TODO send http
    }


    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
