package com.helix.demo.java.util.concurrent;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService 使用测试
 *
 * @author lijianyu@yunloan.net
 * @date 2021-01-05 13:30
 */
public class ScheduleTest {

    /**
     * 测试{@link ScheduledExecutorService#scheduleWithFixedDelay}是否在运行完成任务之后，才开始间隔时间计算
     *
     * @throws InterruptedException
     */
    @Test
    public void simpleScheduled() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        System.out.println("begin:" + getCurrentDate());

        service.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("process 1:" + getCurrentDate());
                Thread.sleep(5000L);
                System.out.println("process 2:" + getCurrentDate());
            } catch (Exception e) {
                System.out.println("=======1");
                e.printStackTrace();
            }
        }, 2000, 2000, TimeUnit.MILLISECONDS);

        Thread.sleep(30000);
        System.out.println("ScheduledExecutorService shutdown:" + getCurrentDate());
//        service.shutdown();
        Thread.sleep(12000);
        System.out.println("ScheduledExecutorService end:" + getCurrentDate());
    }

    /**
     * {@link ScheduledExecutorService#shutdown}终止任务运行测试
     *
     * @throws InterruptedException
     */
    @Test
    public void scheduledShutdown() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        System.out.println("begin:" + getCurrentDate());

        service.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("process 1:" + getCurrentDate());
                Thread.sleep(2000L);
                System.out.println("process 2:" + getCurrentDate());

                //终止定时任务
                service.shutdown();
            } catch (InterruptedException e) {
                System.out.println("=======1");
                e.printStackTrace();
            }
        }, 2000, 5000, TimeUnit.MILLISECONDS);

        Thread.sleep(30000);
        System.out.println("ScheduledExecutorService shutdown:" + getCurrentDate());
//        service.shutdown();
        Thread.sleep(12000);
        System.out.println("ScheduledExecutorService end:" + getCurrentDate());
    }

    /**
     * 任务的任务有异常超出，任务就停止了,但ScheduledExecutorService shutdown termilnate都是false，需要另外关闭
     * {@link ScheduledExecutorService#shutdown}
     *
     * @throws InterruptedException
     */
    @Test
    public void scheduledShutdownOfException() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        System.out.println("begin:" + getCurrentDate());

        service.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("process 1:" + getCurrentDate());
                Thread.sleep(2000L);
                System.out.println("process 2:" + getCurrentDate());

                throw new RuntimeException("任务异常了......");
                //终止定时任务
            } catch (InterruptedException e) {
                System.out.println("=======1");
                e.printStackTrace();
            }
        }, 2000, 5000, TimeUnit.MILLISECONDS);

        Thread.sleep(30000);
        System.out.println("ScheduledExecutorService shutdown:" + getCurrentDate() + "，is shutdown：" + service.isShutdown() + "，is terminated：" + service.isTerminated());
        service.shutdown();
        Thread.sleep(12000);
        System.out.println("ScheduledExecutorService end:" + getCurrentDate());
    }


    /**
     * 多任务
     *
     * @throws InterruptedException
     */
    @Test
    public void multiScheduled() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        System.out.println("begin:" + getCurrentDate());

        service.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("process A1:" + getCurrentDate());
                Thread.sleep(2000L);
                System.out.println("process A2:" + getCurrentDate());
                //终止定时任务
            } catch (InterruptedException e) {
                System.out.println("=======A1");
                e.printStackTrace();
            }
        }, 2000, 5000, TimeUnit.MILLISECONDS);

        service.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("process B1:" + getCurrentDate());
                Thread.sleep(2000L);
                System.out.println("process B2:" + getCurrentDate());
                //终止定时任务
            } catch (InterruptedException e) {
                System.out.println("=======B1");
                e.printStackTrace();
            }
        }, 3000, 7000, TimeUnit.MILLISECONDS);

        Thread.sleep(30000);
        System.out.println("ScheduledExecutorService begin shutdown" + getCurrentDate());
        service.shutdown();
        Thread.sleep(12000);
        System.out.println("ScheduledExecutorService end:" + getCurrentDate());
    }

    /**
     * 任务数，多余线程数；任务时间调度情况；
     * 下面例子情况，在任务执行完成，就马上计算好下一次执行时间，线程有空闲就马上执行
     *
     * @throws InterruptedException
     */
    @Test
    public void taskMoreThanPoolsize() throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        System.out.println("begin:" + getCurrentDate());

        service.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("process A1:" + getCurrentDate());
                Thread.sleep(2000L);
                System.out.println("process A2:" + getCurrentDate());
            } catch (InterruptedException e) {
                System.out.println("=======A1");
                e.printStackTrace();
            }
        }, 2000, 5000, TimeUnit.MILLISECONDS);

        service.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("process B1:" + getCurrentDate());
                Thread.sleep(2000L);
                System.out.println("process B2:" + getCurrentDate());
                //终止定时任务
            } catch (InterruptedException e) {
                System.out.println("=======B1");
                e.printStackTrace();
            }
        }, 3000, 7000, TimeUnit.MILLISECONDS);

        Thread.sleep(30000);
        System.out.println("ScheduledExecutorService begin shutwon" + getCurrentDate());
        service.shutdown();
        Thread.sleep(12000);
        System.out.println("ScheduledExecutorService end:" + getCurrentDate());
    }

    public String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return df.format(new Date());
    }
}
