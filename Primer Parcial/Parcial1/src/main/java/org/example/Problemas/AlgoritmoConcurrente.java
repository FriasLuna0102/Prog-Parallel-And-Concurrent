package org.example.Problemas;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class AlgoritmoConcurrente {
    private static final int QUEUE_CAPACITY = 10;
    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 2;
    private static final int PRODUCE_COUNT = 100;
    private static final AtomicInteger itemsProduced = new AtomicInteger(0);
    private static final AtomicInteger itemsConsumed = new AtomicInteger(0);

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

        // Crear y iniciar los productores
        Thread[] producers = new Thread[PRODUCER_COUNT];
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            producers[i] = new Thread(new Producer(queue, PRODUCE_COUNT / PRODUCER_COUNT));
            producers[i].start();
        }

        // Crear y iniciar los consumidores
        Thread[] consumers = new Thread[CONSUMER_COUNT];
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            consumers[i] = new Thread(new Consumer(queue));
            consumers[i].start();
        }

        // Esperar a que todos los hilos terminen
        for (Thread producer : producers) {
            try {
                producer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Thread consumer : consumers) {
            try {
                consumer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Todos los hilos han terminado.");
    }

    //He creado esta clase en el ALgoritmoConcurrente para llevar una mejor organización del código.
    //Ya que se mantiene lo referente a un problema en una sola clase.
    static class Producer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final int produceCount;
        private final Random random = new Random();

        Producer(BlockingQueue<Integer> queue, int produceCount) {
            this.queue = queue;
            this.produceCount = produceCount;
        }

        @Override
        public void run() {
            for (int i = 0; i < produceCount; i++) {
                int number = random.nextInt(100);
                try {
                    queue.put(number);
                    itemsProduced.incrementAndGet();
                    System.out.println("Productor " + Thread.currentThread().getName() + " produjo: " + number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //He creado esta clase en el ALgoritmoConcurrente para llevar una mejor organización del código.
    //Ya que se mantiene lo referente a un problema en una sola clase.
    static class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private int sum = 0;

        Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (itemsConsumed.get() < PRODUCE_COUNT) {
                try {
                    int number = queue.take();
                    sum += number;
                    itemsConsumed.incrementAndGet();
                    System.out.println("Consumidor " + Thread.currentThread().getName() + " consumió: " + number + ", suma: " + sum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
