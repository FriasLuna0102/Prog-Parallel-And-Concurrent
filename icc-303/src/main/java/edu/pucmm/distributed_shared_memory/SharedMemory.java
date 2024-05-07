package edu.pucmm.distributed_shared_memory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 16:31
 */
public class SharedMemory {

    /*
    Memoria Compartida:

    Imagina un sistema de procesamiento de datos en tiempo real para monitorear y controlar un sistema de producción
    industrial. Cada sensor y actuador en la planta está conectado a un nodo de procesamiento que realiza análisis en
    tiempo real para garantizar un funcionamiento eficiente y seguro. En este caso:

    * Los nodos de procesamiento pueden compartir una memoria centralizada donde almacenan los datos de los sensores,
        los resultados de los análisis y las instrucciones para los actuadores.
    * La arquitectura de memoria compartida simplifica la comunicación entre los nodos de procesamiento, lo que permite
        una respuesta rápida a los cambios en el entorno de producción.
    * Sin embargo, si la planta crece significativamente y se añaden más nodos de procesamiento, la memoria compartida
        puede convertirse en un cuello de botella, limitando la escalabilidad del sistema.
    */

    static class Inventory {
        private int stock = 0;

        public synchronized void addItem(int quantity) {
            stock += quantity;
            System.out.println(Thread.currentThread().getName() + " added " + quantity + " items. Stock: " + stock);
        }

        public synchronized void sellItem(int quantity) {
            if (stock >= quantity) {
                stock -= quantity;
                System.out.println(Thread.currentThread().getName() + " sold " + quantity + " items. Stock: " + stock);
            } else {
                System.out.println(Thread.currentThread().getName() + " couldn't sell " + quantity + " items. Not enough stock.");
            }
        }
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable producer = () -> {
            for (int i = 0; i < 5; i++) {
                inventory.addItem(1);
            }
        };

        Runnable consumer = () -> {
            for (int i = 0; i < 5; i++) {
                inventory.sellItem(1);
            }
        };

        executor.execute(producer);
        executor.execute(consumer);

        executor.shutdown();
    }
}
