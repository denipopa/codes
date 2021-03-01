package Benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 5)
@Measurement(iterations = 5)
//for context switch

public class MultithreadingBench implements Runnable{
    private Thread t;
    private String threadName;

//    public static void main(String[] args) throws RunnerException {
//        MultithreadingBench thread1 = new MultithreadingBench();
//        thread1.threadName="thread 1";
//        thread1.start();
//        MultithreadingBench thread2 = new MultithreadingBench();
//       thread2.threadName="THREAD2";
//        thread2.start();
//        Options opt = new OptionsBuilder()
//                .include(MultithreadingBench.class.getSimpleName())
//                .resultFormat(ResultFormatType.CSV)
//                .result("C:\\Users\\denip\\Desktop\\facultate\\scsJavaProj\\benchmark-resultMULTIThreads" + System.currentTimeMillis() + ".csv")
//                .forks(1)
//                .build();
//
//        new Runner(opt).run();
//    }
    @Benchmark
    public void run() {
        System.out.println("Running " +  threadName );
        try {
            for(int i = 1; i < 3; i++) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }
    @Benchmark
    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }


}
