package com.helix.demo.forkjoin;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * sum数组值
 * @author lijianyu
 * @date 2023/3/24 下午3:08
 */
public class SumTask extends RecursiveTask<Long> {

    private long[] array;

    private int SHARDING = 500;

    public SumTask(long[] array){
        this.array = array;
    }

    @Override
    protected Long compute() {
        if(array.length>SHARDING){
            int length = array.length / 2;

            long[] left = Arrays.copyOf(array, length);
            long[] right = Arrays.copyOfRange(array, length,array.length);

            System.out.println(String.format("thread name:%s,left size:%d,right size:%d",Thread.currentThread().getName(),left.length,right.length));

            SumTask leftTask = new SumTask(left);
            SumTask rightTask = new SumTask(right);
            invokeAll(leftTask,rightTask);
            Long leftSum = leftTask.join();
            Long rightSum = rightTask.join();
            return leftSum + rightSum;
        }

        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum = sum + array[i];
        }

        return sum;
    }
}
