package edu.pucmm.message_passing.example1;

public class ConsumidorImprimir extends Consumidor{
    @Override
    public ConsumidorLic procesarMensaje(Mensaje mensaje) {

        Persona persona = (Persona) mensaje.getContenido();

        System.out.println("Nombre de la persona: "+ persona.getNombre());
        System.out.println("Apellido de la persona: "+ persona.getApellido());
        System.out.println("Edad de la persona: "+ persona.getEdad());

        return null;
    }
}
