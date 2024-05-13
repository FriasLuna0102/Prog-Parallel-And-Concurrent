package org.example.implementacion;

public abstract class Proceso {

    protected Canal canal;

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    abstract void recibirMensaje(Mensaje<?> mensaje);
}
