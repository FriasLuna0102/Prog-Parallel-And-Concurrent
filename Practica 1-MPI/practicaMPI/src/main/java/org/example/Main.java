package org.example;

import org.example.implementacion.Canal;
import org.example.implementacion.MatrizConsumidor;
import org.example.implementacion.MatrizMultiplicadora;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Crear matrices A y B
        double[][] matrizA = {{1, 2}, {3, 4}};
        double[][] matrizB = {{5, 6}, {7, 8}};

        // Multiplicación secuencial de matrices
        double[][] resultadoSecuencial = multiplicacionSecuencial(matrizA, matrizB);

        // Multiplicación paralela de matrices
        Canal canal = new Canal();
        MatrizMultiplicadora multiplicadora = new MatrizMultiplicadora(matrizA, matrizB);
        MatrizConsumidor consumidor = new MatrizConsumidor(canal);
        multiplicadora.setCanal(canal);
        consumidor.setCanal(canal);
        canal.registrarProceso(multiplicadora);
        canal.registrarProceso(consumidor);
        multiplicadora.calcular();
        double[][] resultadoParalelo = consumidor.recopilarResultados(multiplicadora.getResultadoParcial());

        // Comparación de resultados
        System.out.println("Resultado de la multiplicación de matrices secuencial:");
        imprimirMatriz(resultadoSecuencial);
        System.out.println();
        System.out.println("Resultado de la multiplicación de matrices paralela:");
        imprimirMatriz(resultadoParalelo);
        System.out.println("¿Los resultados son iguales?: " + compararMatrices(resultadoSecuencial, resultadoParalelo));
    }

    // Método para realizar la multiplicación de matrices de forma secuencial
    private static double[][] multiplicacionSecuencial(double[][] matrizA, double[][] matrizB) {
        int filasA = matrizA.length;
        int columnasB = matrizB[0].length;
        int filasB = matrizB.length;
        double[][] resultado = new double[filasA][columnasB];

        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                for (int k = 0; k < filasB; k++) {
                    resultado[i][j] += matrizA[i][k] * matrizB[k][j];
                }
            }
        }

        return resultado;
    }

    // Método para imprimir una matriz
    private static void imprimirMatriz(double[][] matriz) {
        for (double[] fila : matriz) {
            System.out.println(Arrays.toString(fila));
        }
    }

    // Método para comparar dos matrices
    private static boolean compararMatrices(double[][] matrizA, double[][] matrizB) {
        if (matrizA.length != matrizB.length || matrizA[0].length != matrizB[0].length) {
            return false;
        }

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[0].length; j++) {
                if (matrizA[i][j] != matrizB[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}
