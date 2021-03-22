package com.wushiyii.lock;


import com.wushiyii.DistributeLock;
import com.wushiyii.DistributeLockBuilder;
import com.wushiyii.DistributeLockClient;
import org.junit.Test;

public class EtcdClientTest extends BaseTest{

    @Test
    public void test1() throws InterruptedException {
        DistributeLockClient etcdClient = DistributeLockBuilder.etcd().urls("http://192.168.188.49:2379").build();
        DistributeLock lock1 = etcdClient.getLock("lock1");
        print(lock1.lock(5));

        print(lock1.lock(5));

        DistributeLock lock2 = etcdClient.getLock("lock2");
        print(lock2.lock(5));

        Thread.sleep(6000);
        print(lock2.lock(5));

    }
}
