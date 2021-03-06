package Benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import net.openhft.affinity.Affinity;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class BenchThreads extends Thread {
    public static int amount = 0;
    public static int N = 10000;
//    public static void main(String[] args) throws RunnerException {
//            BenchThreads thread = new BenchThreads();
//            thread.start();
//            // Wait for the thread to finish
//            while (thread.isAlive()) {
//                System.out.println("Waiting...");
//
//            }
//        // Update amount and print its value
//        System.out.println(amount);
//        Options opt = new OptionsBuilder()
//                .include(BenchThreads.class.getSimpleName())
//                .resultFormat(ResultFormatType.CSV)
//                .result("C:\\Users\\denip\\Desktop\\facultate\\scsJavaProj\\benchmark-resultThreads" + System.currentTimeMillis() + ".csv")
//                .forks(1)
//                .build();
//
//        new Runner(opt).run();
//
//    }

    @Benchmark
    public void threadCreation(){
        BenchThreads thread = new BenchThreads();
            thread.start();
            // Wait for the thread to finish
            while (thread.isAlive()) {
                System.out.println("Waiting...");

            }
            System.out.println(amount);
    }

    public void run() {
        for (int i = 0; i < N; i++)
            amount++;
    }
    @Benchmark
    public void contextSwitch() {
        CubbyHole c = new CubbyHole();
        Producer p1 = new Producer(c, 1);
        Consumer c1 = new Consumer(c, 1);
        p1.start();
        c1.start();
    }

    @Benchmark
    public void migration(){
        Affinity.setAffinity(3);
        long startTime=System.nanoTime();
        Affinity.setAffinity(5);
        long stopTime=System.nanoTime();
        System.out.println(stopTime-startTime);
    }
}
//    class ProducerConsumerTest {
//        @Benchmark
//        public void contextSwitch() {
//            CubbyHole c = new CubbyHole();
//            Producer p1 = new Producer(c, 1);
//            Consumer c1 = new Consumer(c, 1);
//            p1.start();
//            c1.start();
//        }
//    }
    class CubbyHole {
        private int contents;
        private boolean available = false;

        public synchronized int get() {
            while (available == false) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
            available = false;
            notifyAll();
            return contents;
        }
        public synchronized void put(int value) {
            while (available == true) {
                try {
                    wait();
                } catch (InterruptedException e) { }
            }
            contents = value;
            available = true;
            notifyAll();
        }
    }
    class Consumer extends Thread {
        private CubbyHole cubbyhole;
        private int number;

        public Consumer(CubbyHole c, int number) {
            cubbyhole = c;
            this.number = number;
        }
        public void run() {
            int value = 0;
            for (int i = 0; i < 10; i++) {
                value = cubbyhole.get();
                System.out.println("Consumer #" + this.number + " got: " + value);
            }
        }
    }
    class Producer extends Thread {
        private CubbyHole cubbyhole;
        private int number;
        public Producer(CubbyHole c, int number) {
            cubbyhole = c;
            this.number = number;
        }
        public void run() {
            for (int i = 0; i < 10; i++) {
                cubbyhole.put(i);
                System.out.println("Producer #" + this.number + " put: " + i);
                try {
                    sleep((int)(Math.random() * 100));
                } catch (InterruptedException e) { }
            }
        }
    }


