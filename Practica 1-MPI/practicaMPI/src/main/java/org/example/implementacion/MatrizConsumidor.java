package org.example.implementacion;

public class MatrizConsumidor extends Proceso {

    private Canal canal;
    private double[][] resultadoParcial; // Agregar esta variable

    public MatrizConsumidor(Canal canal) {
        this.canal = canal;
    }


    @Override
    void recibirMensaje(Mensaje<?> mensaje) {
        if (mensaje instanceof Fila) {
            Fila fila = (Fila) mensaje;
            double[] contenido = fila.getContenido();
            int filaIndex = fila.getIndice();
            // Verificar si resultadoParcial es nulo y inicializarlo si es necesario
            if (resultadoParcial == null) {
                resultadoParcial = new double[contenido.length][contenido.length];
            }
            // Insertar la fila recibida en la matriz resultante completa
            System.arraycopy(contenido, 0, resultadoParcial[filaIndex], 0, contenido.length);
        }
    }

    public double[][] recopilarResultados(double[][] resultadoParcial) {
        return resultadoParcial; // Por ahora simplemente devolvemos los resultados parciales
    }

}