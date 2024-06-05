package org.example;

import java.util.Random;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        int n = 10000000;
        int[] array = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(1000000);
        }

        // Clone the array for parallel sorting
        int[] parallelArray = array.clone();

        // Sequential Quicksort
        long startTime = System.currentTimeMillis();
        quicksort(array, 0, array.length - 1);
        long endTime = System.currentTimeMillis();
        System.out.println("Sequential Quicksort Time: " + (endTime - startTime) + " ms");

        // Parallel Quicksort
        ForkJoinPool pool = new ForkJoinPool();
        startTime = System.currentTimeMillis();
        pool.invoke(new QuicksortTask(parallelArray, 0, parallelArray.length - 1));
        endTime = System.currentTimeMillis();
        System.out.println("Parallel Quicksort Time: " + (endTime - startTime) + " ms");
    }

    // Sequential Quicksort
    public static void quicksort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quicksort(array, low, pi - 1);
            quicksort(array, pi + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    // Parallel Quicksort
    public static class QuicksortTask extends RecursiveAction {
        private final int[] array;
        private final int low;
        private final int high;
        private static final int THRESHOLD = 10000;

        public QuicksortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (high - low < THRESHOLD) {
                quicksort(array, low, high);
            } else {
                int pi = partition(array, low, high);
                QuicksortTask leftTask = new QuicksortTask(array, low, pi - 1);
                QuicksortTask rightTask = new QuicksortTask(array, pi + 1, high);
                invokeAll(leftTask, rightTask);
            }
        }

        private int partition(int[] array, int low, int high) {
            int pivot = array[high];
            int i = (low - 1);
            for (int j = low; j < high; j++) {
                if (array[j] < pivot) {
                    i++;
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            int temp = array[i + 1];
            array[i + 1] = array[high];
            array[high] = temp;
            return i + 1;
        }
    }
}
