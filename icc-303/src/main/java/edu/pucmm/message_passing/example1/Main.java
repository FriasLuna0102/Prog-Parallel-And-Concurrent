package edu.pucmm.message_passing.example1;

/**
 * @author me@fredpena.dev
 * @created 06/05/2024  - 15:49
 */
public class Main {

    public static void main(String[] args) {
        Canal canal = new Canal();

        Productor productor = new Productor(canal) {
            @Override
            public void publicar(Mensaje mensaje) {
                super.publicar(mensaje);
            }
        };

        Consumidor consumidor = new Consumidor() {
            @Override
            public ConsumidorLic procesarMensaje(Mensaje mensaje) {
                System.out.println("Consumidor: Acabo de recibir: " + mensaje.getContenido());
                return null;
            }
        };

        Canal canal2 = new Canal();


        //Practica: /////////////////////
        Persona persona = new Persona("Starlin",20,"Frias");
        Mensaje<Persona> mensaje2 = new Mensaje<>(persona);

        ConsumidorLic consumidorLic = new ConsumidorLic();
        ConsumidorImprimir consumidorImprimir = new ConsumidorImprimir();

        canal2.registrarConsumidor(consumidorLic);
        canal2.registrarConsumidor(consumidorImprimir);

        Productor productor2 = new Productor(canal2) {
            @Override
            public void publicar(Mensaje mensaje2) {
                super.publicar(mensaje2);
            }
        };

        productor2.publicar(mensaje2);




//////////////////////////////////




        canal.registrarConsumidor(consumidor);
        Mensaje<String> msg = new Mensaje<>("Hello World!");
        productor.publicar(msg);

        Mensaje<Integer> msg2 = new Mensaje<>(123456);
        productor.publicar(msg2);

    }
}