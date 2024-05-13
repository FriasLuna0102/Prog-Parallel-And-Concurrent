package org.example.implementacion;


/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 15:47
 */
class Mensaje<T> {

    private T contenido;

    Mensaje(T contenido) {
        this.contenido = contenido;
    }

    void setContenido(T contenido) {
        this.contenido = contenido;
    }

    T getContenido() {
        return contenido;
    }
}
