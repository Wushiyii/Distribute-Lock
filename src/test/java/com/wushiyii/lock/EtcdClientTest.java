package com.wushiyii.lock;


import com.wushiyii.DistributeLock;
import com.wushiyii.DistributeLockBuilder;
import com.wushiyii.DistributeLockClient;
import org.junit.Test;

public class EtcdClientTest extends BaseTest{

    @Test
    public void test1() {
        DistributeLockClient etcdClient = DistributeLockBuilder.etcd().urls("http://127.0.0.1:2379").build();
        DistributeLock lock1 = etcdClient.getLock("lock1");
        print(lock1.lock(2));

        print(lock1.lock(2));

        DistributeLock lock2 = etcdClient.getLock("lock2");
        print(lock2.lock(2));

        print(lock2.lock(2));

    }
}
