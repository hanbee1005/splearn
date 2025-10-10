package tobyspring.learningtest.archunit.application;

public class MyService {
    private MyService2 myService2;

    void run() {
        myService2 = new MyService2();
        System.out.println(myService2);
    }
}
