package edu.pucmm.message_passing.example1;

/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 15:49
 */
public abstract class Productor {

    private Canal canal;

    public Productor(Canal canal){
        this.canal = canal;
    }

    public void publicar(Mensaje mensaje){
        canal.pasarMensaje(mensaje);
    }

}
