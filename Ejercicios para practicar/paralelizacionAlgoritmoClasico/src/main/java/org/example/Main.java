package org.example;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Tamaño del arreglo
        int n = 1000000;
        //Numero de hilos
        int numThreads = 4;

        // Crear el arreglo.
        int[] randomArray = new int[n];

        // Create a Random object
        Random random = new Random();

        // Assign random values to the array
        for (int i = 0; i < n; i++) {
            randomArray[i] = random.nextInt(10);
        }

        // Calculate the sum sequentially
        long startTime = System.currentTimeMillis();
        int sequentialSum = calculateSumSequentially(randomArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Sequential Sum: " + sequentialSum);
        System.out.println("Sequential Time: " + (endTime - startTime) + " ms");

        // Calculate the sum in parallel
        startTime = System.currentTimeMillis();
        int parallelSum = calculateSumInParallel(randomArray, numThreads);
        endTime = System.currentTimeMillis();
        System.out.println("Parallel Sum: " + parallelSum);
        System.out.println("Parallel Time: " + (endTime - startTime) + " ms");
    }

    public static int calculateSumSequentially(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    public static int calculateSumInParallel(int[] array, int numThreads) throws InterruptedException {
        int length = array.length;
        int chunkSize = length / numThreads;
        SumThread[] threads = new SumThread[numThreads];
        int[] partialSums = new int[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? length : start + chunkSize;
            threads[i] = new SumThread(array, start, end, partialSums, i);
            threads[i].start();
        }

        for (SumThread thread : threads) {
            thread.join();
        }

        int totalSum = 0;
        for (int sum : partialSums) {
            totalSum += sum;
        }

        return totalSum;
    }
}

class SumThread extends Thread {
    private final int[] array;
    private final int start;
    private final int end;
    private final int[] partialSums;
    private final int index;

    public SumThread(int[] array, int start, int end, int[] partialSums, int index) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.partialSums = partialSums;
        this.index = index;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        partialSums[index] = sum;
    }
}
