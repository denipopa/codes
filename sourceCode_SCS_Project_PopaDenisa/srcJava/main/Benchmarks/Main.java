package Benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class Main {

    @Param({"10000"})
    private static int N=10000;
    private static List<Book> DATA_FOR_TESTING_ACCESS;
    private static String name="bookName456";
    public static void main(String[] args) throws RunnerException {
        DATA_FOR_TESTING_ACCESS = Book.createBooks( N);
        BenchThreads th=new BenchThreads();
        th.migration();
        System.out.println(findUsingEnhancedForLoop());
        System.out.println(findWithStreams());
        System.out.println(findContains());
        System.out.println(findWithIndexOf());
        Options opt = new OptionsBuilder()
                .include(Main.class.getSimpleName())
                .include(BenchThreads.class.getSimpleName())
                //.include(MultithreadingBench.class.getSimpleName())
                .resultFormat(ResultFormatType.CSV)
                .result("C:\\Users\\denip\\Desktop\\facultate\\scsJavaProj\\benchmark-resultAll" + System.currentTimeMillis() + ".csv")
                .forks(1)
                .build();

        new Runner(opt).run();

    }

    @Setup
    public void setup() {
        List<Book> DATA_FOR_TESTING_ALLOCATION = createDataHeap();
        DATA_FOR_TESTING_ACCESS = Book.createBooks(N);
    }

//    @Benchmark
//    public void loopFor(Blackhole bh) {
//        for (int i = 0; i < DATA_FOR_TESTING.size(); i++) {
//            String s = DATA_FOR_TESTING.get(i); //take out n consume, fair with foreach
//            bh.consume(s);
//        }
//    }
//
//    @Benchmark
//    public void loopWhile(Blackhole bh) {
//        int i = 0;
//        while (i < DATA_FOR_TESTING.size()) {
//            String s = DATA_FOR_TESTING.get(i);
//            bh.consume(s);
//            i++;
//        }
//    }
//
//    @Benchmark
//    public void loopForEach(Blackhole bh) {
//        for (String s : DATA_FOR_TESTING) {
//            bh.consume(s);
//        }
//    }
//
//
//    public void loopIterator(Blackhole bh) {
//        Iterator<String> iterator = DATA_FOR_TESTING.iterator();
//        while (iterator.hasNext()) {
//            String s = iterator.next();
//            bh.consume(s);
//        }
//    }
//

//
    @Benchmark
    public int createDataStack() {

        int data=0;
        for (int i = 0; i < N; i++) {
            data+=1;
        }
        return data;
    }
@Benchmark
public List<Book> createDataHeap() {

    List<Book> books = new ArrayList<>();
    for (int i = 0; i < N; i++) {
        books.add(new Book(i, "bookName" + i));
    }
    return books;
}



    @Benchmark
    public static Book findContains(){
        Book james = new Book(2, "bookName456");
        if (DATA_FOR_TESTING_ACCESS.contains(james)) {
            return james;
        }
        else return null;
    }
    @Benchmark
    public static Book findWithIndexOf() {
        Book james = new Book(2, "bookName456");
        if (DATA_FOR_TESTING_ACCESS.indexOf(james) != -1) {
            return james;
        } else return null;
    }

    @Benchmark
    public static Book findUsingEnhancedForLoop() {

        for (Book book:DATA_FOR_TESTING_ACCESS) {
            if (book.getName().equals(name)) {
                return book;
            }
        }
        return null;
    }

    @Benchmark
    public static Book findWithStreams(){
        Book james =DATA_FOR_TESTING_ACCESS.stream()
        .filter(book->"bookName456".equals(book.getName()))
        .findAny()
        .orElse(null);
        return james;
    }


}
