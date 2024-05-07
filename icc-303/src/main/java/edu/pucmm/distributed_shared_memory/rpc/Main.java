package edu.pucmm.distributed_shared_memory.rpc;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 16:41
 */
public class Main {

    public static void main(String[] args) {
        try {
            InventoryServiceImpl inventory1 = new InventoryServiceImpl();
            inventory1.createStubAndBind();

            Registry registry = LocateRegistry.getRegistry();
            InventoryService server = (InventoryService) registry.lookup("InventoryService");

            // Simulación de agregar y vender productos
            server.addItem(10);
            server.sellItem(5);

            // Obtener el stock de cada inventario
            System.out.println("Stock en Inventory1: " + inventory1.getStock());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
