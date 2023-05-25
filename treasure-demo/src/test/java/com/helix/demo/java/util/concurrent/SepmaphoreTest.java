package com.helix.demo.java.util.concurrent;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lijianyu
 * @date 2023/5/23 14:09
 */
public class SepmaphoreTest {

    /**
     * 信号量测试
     * 10个线程，信号量5个
     * 测试结构：前面5个线程同时获取信号量，处理业务逻辑。后面5个线程阻塞直到前面线程有完成任务，释放信号量为止；
     * @throws InterruptedException
     */
    @Test
    public void simple() throws InterruptedException {
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 10; i++) {
            semaphore.acquire();
            Thread t = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"==="+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000,10000));
                    semaphore.release();
                } catch (InterruptedException e) {
                }
            });
            t.setName("thread:"+i);
            t.start();
            t.join();
        }

        while(semaphore.getQueueLength() != 0){
            System.out.println("sleep=="+semaphore.getQueueLength());
            Thread.sleep(1000);
        }
        System.out.println("thread sleep");
    }

}
