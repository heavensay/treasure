package com.helix.demo.forkjoin;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author lijianyu
 * @date 2023/3/24 下午2:55
 */
public class TestForkJoin {

    /**
     * fork join
     * 大数组求和，利用数组分拆多个小数组求和，多任务运行，在多cpu机器上提高性能来实现
     */
    @Test
    public void sum(){
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        long[] array = new long[2400];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Random().nextInt(1000);
        }

        Long sum = forkJoinPool.invoke(new SumTask(array));
        System.out.println("fork/join result:"+sum);
    }
}
