package com.hujing.netty.atomic;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerUpdateDemo {


    public static void main(String[] args) {
        Person person = new Person();
        AtomicIntegerFieldUpdater<Person> updater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(updater.getAndIncrement(person));
            }).start();
        }
    }
}


class Person {
    volatile int age = 1;
}