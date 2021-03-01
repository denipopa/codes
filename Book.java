package Benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Book {
    public int id;
    public String name;
    private static List<Book> books = new ArrayList<>();
    //public Integer param;
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id &&
                name.equals(book.name);
    }


    public Book() {
//       this.id=getId();
//       this.name=getName();
    }

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static List<Book> createBooks(int param) {
        for (int i = 0; i < param; i++) {
            books.add(new Book(i, "bookName" + i));
        }
        return books;
    }


}
