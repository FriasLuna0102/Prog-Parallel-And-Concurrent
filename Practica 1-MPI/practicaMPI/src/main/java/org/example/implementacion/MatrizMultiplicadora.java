package org.example.implementacion;

public class MatrizMultiplicadora extends Proceso {

    private double[][] matrizA;
    private double[][] matrizB;
    private double[][] resultadoParcial;

    public MatrizMultiplicadora(double[][] matrizA, double[][] matrizB) {
        this.matrizA = matrizA;
        this.matrizB = matrizB;
    }

    @Override
    public void recibirMensaje(Mensaje<?> mensaje) {
        if (mensaje instanceof Fila) {
            Fila fila = (Fila) mensaje;
            int filaIndex = fila.getIndice();
            double[] contenido = fila.getContenido();
            multiplicarFila(filaIndex, contenido);
        }
    }

    private void multiplicarFila(int filaIndex, double[] fila) {
        for (int i = 0; i < matrizB[0].length; i++) {
            double suma = 0;
            for (int j = 0; j < fila.length; j++) {
                suma += matrizA[filaIndex][j] * matrizB[j][i];
            }
            resultadoParcial[filaIndex][i] = suma;
        }
    }

    public void calcular() {
        resultadoParcial = new double[matrizA.length][matrizB[0].length];
        for (int i = 0; i < matrizA.length; i++) {
            Mensaje<double[]> mensaje = new Fila(i, matrizA[i]);
            canal.enviarMensaje(this, mensaje);
        }
    }

    public double[][] getResultadoParcial() {
        return resultadoParcial;
    }
}
