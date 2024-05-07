package edu.pucmm.distributed_shared_memory.rpc;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 16:41
 */
class InventoryServiceImpl implements InventoryService {
    private int stock = 0;

    public void createStubAndBind() throws RemoteException {
        InventoryService stub = (InventoryService) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(1199);
        registry.rebind("InventoryService", stub);
    }

    @Override
    public void addItem(int quantity) throws RemoteException {
        stock += quantity;
        System.out.println("Added " + quantity + " items. Stock: " + stock);
    }

    @Override
    public void sellItem(int quantity) throws RemoteException {
        if (stock >= quantity) {
            stock -= quantity;
            System.out.println("Sold " + quantity + " items. Stock: " + stock);
        } else {
            System.out.println("Couldn't sell " + quantity + " items. Not enough stock.");
        }
    }

    @Override
    public int getStock() throws RemoteException {
        return stock;
    }
}
