package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Dirección del servidor
        int port = 12345; // Puerto en el que el servidor escucha

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar mensaje al servidor
            String message = "Hola, servidor!";
            out.println(message);
            System.out.println("Mensaje enviado al servidor: " + message);

            // Esperar confirmación del servidor
            String response = in.readLine();
            System.out.println("Respuesta del servidor: " + response);

        } catch (IOException e) {
            System.out.println("Error en la comunicación con el servidor: " + e.getMessage());
        }

    }
}