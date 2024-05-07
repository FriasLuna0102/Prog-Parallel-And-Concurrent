package edu.pucmm.distributed_shared_memory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 16:32
 */
public class DistributedMemory {
    /*
    Memoria Distribuida:

    Ahora, considera un sistema de comercio electrónico a gran escala que opera en todo el mundo. El sistema maneja un
    gran volumen de transacciones de clientes simultáneas y necesita estar disponible las 24 horas del día, los 7 días
    de la semana. En este caso:

    * Cada región geográfica tiene su propio centro de datos con servidores dedicados para manejar las transacciones locales.
    * Los servidores en cada centro de datos tienen su propia memoria local y se comunican entre sí a través de una red distribuida.
    * La arquitectura distribuida permite escalar horizontalmente agregando más centros de datos según sea necesario para
        satisfacer la demanda del negocio, ya que cada centro de datos opera de manera independiente.
    * Sin embargo, la comunicación entre centros de datos puede introducir latencia adicional, por lo que se deben
        implementar estrategias de redundancia y tolerancia a fallos para garantizar la disponibilidad y la consistencia
        de los datos en todo momento.
     */

    static class Inventory {
        private int stock = 0;

        public void addItem(int quantity) {
            stock += quantity;
            System.out.println(Thread.currentThread().getName() + " added " + quantity + " items. Stock: " + stock);
        }

        public void sellItem(int quantity) {
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
