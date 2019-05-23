package com.matrangola.demos;

import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadDemo {

    private static void demo() {
        new Thread(() -> {
            for (int i = 0; i< 100; i++) {
                ZonedDateTime now = ZonedDateTime.now();
                System.out.println("My Thread: " + i + " time: " + now + " Thread: " + Thread.currentThread().getId());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("calling thread: " + Thread.currentThread().getId());
    }

    private static void executor() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10, r -> {
            Thread thread = new Thread(r);
            thread.setName("Demo");
            thread.setDaemon(true);
            return thread;
        });

        ScheduledFuture<?> result = executor.scheduleAtFixedRate(() -> {
            ZonedDateTime now = ZonedDateTime.now();
            System.out.println("My Thread:  time: " + now + " Thread: " + Thread.currentThread().getName() + " " + Thread.currentThread().getId());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, 1,1, TimeUnit.SECONDS);

        System.out.println("Started executor");
        if (result.isCancelled()) System.out.println("canceled");
        Thread.sleep(30000);
        System.out.println("done");
    }

    public static void main(String[] args) throws InterruptedException {
        executor();
    }
}
