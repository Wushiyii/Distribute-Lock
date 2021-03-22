package com.wushiyii.lock.util;

import com.wushiyii.lock.BaseTest;
import com.wushiyii.util.HttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtilTest extends BaseTest {

    @Test
    public void putTest() throws IOException {
        String url = "http://127.0.0.1:2379/v2/keys/";

        Map<String, String> map = new HashMap<>();
        String k1 = "test1";
        map.put("value", "test1");
        map.put("ttl", "10");


        HttpClient client = new HttpClient();
        client.putForm(url + k1, map, new BasicResponseHandler());
    }

}
