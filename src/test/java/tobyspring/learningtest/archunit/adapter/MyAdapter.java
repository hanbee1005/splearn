package tobyspring.learningtest.archunit.adapter;

import tobyspring.learningtest.archunit.application.MyService;

class MyAdapter {
    private MyService myService;

    void run() {
        myService = new MyService();
        System.out.println(myService);
    }
}
