//package com.helix.demo.test.benchmark;
//
//import org.openjdk.jmh.annotations.*;
//import org.openjdk.jmh.runner.Runner;
//import org.openjdk.jmh.runner.RunnerException;
//import org.openjdk.jmh.runner.options.Options;
//import org.openjdk.jmh.runner.options.OptionsBuilder;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 没有测试成功后续在实验
// */
//
//@BenchmarkMode(Mode.Throughput)
////@OutputTimeUnit(TimeUnit.MICROSECONDS) // 输出结果的时间粒度为微秒
//@State(Scope.Benchmark)
//public class BenchmarkTest {
//
//
//    @Benchmark
//    public void sleep() throws Exception{
//        System.out.println("sleep");
////        Thread.sleep(2000L);
//    }
//
//    public static void main(String[] args) throws RunnerException {
//        System.out.println("aaa3");
//        Options options = new OptionsBuilder()
//                .include(BenchmarkTest.class.getName()+".*")
////                .output("E:/Benchmark.log")
//                .forks(2)
//                .warmupIterations(5)
//                .measurementIterations(2)
//                .build();
//        new Runner(options).run();
//    }
//}
