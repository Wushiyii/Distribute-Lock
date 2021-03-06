package com.wushiyii.helper;

import com.alibaba.fastjson.JSON;
import com.wushiyii.exception.DistributeLockException;
import com.wushiyii.model.EtcdResponse;
import com.wushiyii.util.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * ETCD communicator
 * @Author: wgq
 * @Date: 2021/3/19 18:53
 */
public class EtcdHelper {

    private String[] urls;
    private static volatile HttpClient httpClient;
    private static volatile EtcdHelper instance;
    private static final String ETCD_PREFIX = "/v2/keys/";
    private static final List<Integer> SUCCESS_CODE_LIST = Arrays.asList(200, 201);
    private static final Logger log = LoggerFactory.getLogger(EtcdHelper.class);
    private static final Random random = new Random();

    public static EtcdHelper getInstance() {
        if (null == instance) {
            synchronized (EtcdHelper.class) {
                if (null == instance) {
                    instance = new EtcdHelper();
                    httpClient = new HttpClient();
                }
            }
        }
        return instance;
    }

    public void put(String key, String value, Integer ttl, String prevValue, Boolean prevExist) throws IOException {
        HashMap<String, String> payload = new HashMap<>();
        payload.put(EtcdConstants.VALUE, value);
        payload.put(EtcdConstants.PREV_EXIST, prevExist.toString());
        payload.put(EtcdConstants.TTL, ttl.toString());
        String randomUrl = determineClusterUrl(key, urls);
        EtcdResponse etcdResponse = httpClient.putForm(randomUrl, payload, etcdResponseHandler(randomUrl));

        if (Objects.nonNull(etcdResponse) && Objects.nonNull(etcdResponse.getErrorCode())) {
            throw new DistributeLockException(etcdResponse.getErrorCode(), etcdResponse.getMessage());
        }

    }

    public void delete(String key, String prevValue) throws IOException {
        Map<String, String> payload = new HashMap<>();
        if (Objects.nonNull(prevValue) && !"".equals(prevValue)) {
            payload.put(EtcdConstants.PREV_VALUE, prevValue);
        }
        String randomUrl = determineClusterUrl(key, urls);
        EtcdResponse etcdResponse = httpClient.deleteForm(randomUrl, payload, etcdResponseHandler(randomUrl));

        if (Objects.nonNull(etcdResponse) && Objects.nonNull(etcdResponse.getErrorCode())) {
            throw new DistributeLockException(etcdResponse.getErrorCode(), etcdResponse.getMessage());
        }
    }


    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }


    private String determineClusterUrl(String key, String[] url) {
        return url[((key.hashCode()) + random.nextInt(100)) % url.length] + ETCD_PREFIX + key;
    }

    private ResponseHandler<EtcdResponse> etcdResponseHandler(String url) {
        return response -> {

            EtcdResponse etcdResponse = JSON.parseObject(EntityUtils.toString(response.getEntity()), EtcdResponse.class);
            log.info("Http client , url={}, response={}", url, etcdResponse);

            int httpCode = response.getStatusLine().getStatusCode();

            if (!SUCCESS_CODE_LIST.contains(httpCode)) {
                throw new DistributeLockException(Objects.nonNull(etcdResponse) ? etcdResponse.getErrorCode() : httpCode,
                        Objects.nonNull(etcdResponse) ? etcdResponse.getMessage() : response.getStatusLine().getReasonPhrase());
            }

            return etcdResponse;
        };
    }

    private static class EtcdConstants {
        public static final String VALUE = "value";
        public static final String PREV_VALUE = "prevValue";
        public static final String PREV_EXIST = "prevExist";
        public static final String TTL = "ttl";
    }
}
