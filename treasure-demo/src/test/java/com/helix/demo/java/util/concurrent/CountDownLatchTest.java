package com.helix.demo.java.util.concurrent;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lijianyu
 * @date 2023/5/25 15:15
 */
public class CountDownLatchTest {

    /**
     * 简单测试
     * 场景：5个人赛跑，所有人跑完的时候，才打印成绩退出主线程；
     */
    @Test
    public void simple() throws InterruptedException {
        CountDownLatch cdLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread("thread"+i){
                @Override
                public void run() {
                    try {
                        String beginTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
                        Thread.sleep(ThreadLocalRandom.current().nextLong(1000,10000));
                        System.out.println(Thread.currentThread().getName()+" is running from:"+beginTime+
                                ",end:"+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }finally {
                        cdLatch.countDown();
                    }
                }
            }.start();
        }

        cdLatch.await();
        System.out.println("所有线程执行完毕");
    }

}
