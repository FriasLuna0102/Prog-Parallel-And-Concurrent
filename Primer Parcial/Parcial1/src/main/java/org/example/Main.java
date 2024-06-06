package org.example;

import org.example.Problemas.AlgoritmoConcurrente;
import org.example.Problemas.BusquedaMatrix;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {

        System.out.println("Problema 1: Paralelización de Búsqueda en una Matriz:\n");
       BusquedaMatrix.main(args);
       System.out.println("----");
       System.out.println("----");
       System.out.println("----");

        System.out.println("Problema 2: Implementación de un Algoritmo Concurrente de Productor-Consumidor:\n");




        //PARA MEDIR EL TIEMPO DE LA EJECUCION DE AlgoritmoConcurrente.

        long startTime = System.currentTimeMillis();
        AlgoritmoConcurrente.main(args);
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de Algoritmo Concurrente en milisegundosss: " + (endTime - startTime));



    }





}




































