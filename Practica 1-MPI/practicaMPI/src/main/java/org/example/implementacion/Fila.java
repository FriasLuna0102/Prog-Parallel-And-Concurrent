package org.example.implementacion;

public class Fila extends Mensaje<double[]> {

    private int indice;

    Fila(int indice, double[] contenido) {
        super(contenido);
        this.indice = indice;
    }

    int getIndice() {
        return indice;
    }
}
