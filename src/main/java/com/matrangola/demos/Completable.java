package com.matrangola.demos;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Completable {

    public Future<String> calculateAsync() throws ExecutionException, InterruptedException {

//        Executors.newCachedThreadPool(() -> {
//            System.out.println("calculating");
//            Thread.sleep(500);
//            System.out.println("done calculating");
//            completableFuture.complete("Done");
//            return null;
//        });

        CompletableFuture<String> completableFuture;
        completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("calculating");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("done calculating");
            return "Done";
        }).thenApply(s -> s + " some more work");

        CompletableFuture<String> another1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadId " + Thread.currentThread().getId());
            return "more work";
        });
        CompletableFuture<String> another2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadId2 " + Thread.currentThread().getId());
            return "more work";
        });
        CompletableFuture<String> another3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadId3 " + Thread.currentThread().getId());
            return "more work";
        });

        CompletableFuture<Void> combined = CompletableFuture.allOf(completableFuture, another1, another2, another3);

        combined.get(); // blocks until all done.
        System.out.println("combined " + completableFuture.get() + another2.get() + another3.get());

        return completableFuture;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Completable completable = new Completable();

        Future<String> future = completable.calculateAsync();
        System.out.println("started, waiting");
        String result = future.get();

        System.out.println("result: " + result);
    }

}
