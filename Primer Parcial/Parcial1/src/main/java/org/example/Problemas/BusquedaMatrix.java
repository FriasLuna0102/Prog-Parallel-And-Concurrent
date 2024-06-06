package org.example.Problemas;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


//Como podemos observar al correr este codigo para una matrix de tamaño 1000 x 1000, el tiempo de
//ejecución para la búsqueda secuencial es de 0 milisegundos, mientras que para la búsqueda
//es 2, 1, 3, algo variante.
//Esto sucede debido a que la cantidad es muy pequeña, y
//comunicacion entre los hilos y la sincronizacion como tal toma tiempo de corrida
//debido a ello, el tiempo de ejecucion para la busqueda paralela es mayor que para la busqueda secuencial.

public class BusquedaMatrix {
    private static final int MATRIX_SIZE = 1000;
    private static final int THREAD_COUNT = 4;
    private static final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static final int TARGET = 256; // Número a buscar
    private static final AtomicBoolean found = new AtomicBoolean(false);

    public static void main(String[] args) {
        // Inicializar la matriz con valores aleatorios
        initializeMatrix();

        // Medir el tiempo de ejecución de la búsqueda secuencial
        long startTime = System.currentTimeMillis();
        boolean sequentialResult = sequentialSearch();
        long endTime = System.currentTimeMillis();
        System.out.println("scuencial: " + sequentialResult);
        System.out.println("Tiempo secuencial en milisegundo: " + (endTime - startTime));

        // Medir el tiempo de ejecución de la búsqueda paralela
        startTime = System.currentTimeMillis();
        parallelSearch();
        endTime = System.currentTimeMillis();
        System.out.println("Paralela:" + found.get());
        System.out.println("Tiempo paralela en milisegundo: " + (endTime - startTime));
    }

    private static boolean sequentialSearch() {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (matrix[i][j] == TARGET) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void parallelSearch() {
        Thread[] threads = new Thread[THREAD_COUNT];
        int chunkSize = MATRIX_SIZE / THREAD_COUNT;

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int start = i * chunkSize;
            final int end = (i == THREAD_COUNT - 1) ? MATRIX_SIZE : (i + 1) * chunkSize;
            threads[i] = new Thread(() -> {
                for (int row = start; row < end; row++) {
                    for (int col = 0; col < MATRIX_SIZE; col++) {
                        if (matrix[row][col] == TARGET) {
                            found.set(true);
                            return;
                        }
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initializeMatrix() {
        Random random = new Random();
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = random.nextInt(500);
            }
        }
    }
}

