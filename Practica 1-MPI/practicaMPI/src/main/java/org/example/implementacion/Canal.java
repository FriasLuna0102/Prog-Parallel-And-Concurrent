package org.example.implementacion;

import java.util.LinkedList;
import java.util.List;

public class Canal {

    private List<Proceso> procesos = new LinkedList<>();

    public void registrarProceso(Proceso proceso) {
        procesos.add(proceso);
    }

    public void enviarMensaje(Proceso destino, Mensaje<?> mensaje) {
        destino.recibirMensaje(mensaje);
    }
}
