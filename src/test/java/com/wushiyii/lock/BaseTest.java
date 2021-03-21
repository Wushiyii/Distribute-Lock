package com.wushiyii.lock;

import com.alibaba.fastjson.JSON;

/**
 * @Author: wgq
 * @Date: 2021/2/22 15:03
 */
public class BaseTest {

    protected void print(Object... os) {
        for (Object o : os) {
            if (o instanceof String) {
                System.out.println(">>>>" + o);
            } else {
                System.out.println(">>>>" + JSON.toJSONString(o));
            }
        }
    }
}
