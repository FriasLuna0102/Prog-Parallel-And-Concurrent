package edu.pucmm.distributed_shared_memory.rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 16:40
 */
interface InventoryService extends Remote {

    void addItem(int quantity) throws RemoteException;

    void sellItem(int quantity) throws RemoteException;

    int getStock() throws RemoteException;
}