package edu.pucmm.message_passing.example1;

import java.util.LinkedList;
import java.util.List;

/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 15:48
 */
public class Canal {

    private List<Consumidor> consumidores = new LinkedList<>();

    public void registrarConsumidor(Consumidor consumidor){
        consumidores.add(consumidor);
    }

    public void borrarConsumidor(Consumidor consumidor){
        consumidores.remove(consumidor);
    }

    public void pasarMensaje(Mensaje mensaje){
        consumidores.forEach(consumidor -> {
            consumidor.procesarMensaje(mensaje);
        });
    }
}
