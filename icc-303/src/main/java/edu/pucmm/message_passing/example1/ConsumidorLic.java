package edu.pucmm.message_passing.example1;

public class ConsumidorLic extends Consumidor {
    @Override
    public ConsumidorLic procesarMensaje(Mensaje mensaje) {


        Persona persona = (Persona) mensaje.getContenido();

        if(persona.getEdad() > 18){
            System.out.println(persona.getNombre()+" tiene licencia.");
        }else{
            System.out.println(persona.getNombre()+" es menor de 18.");
        }
        return null;
    }
}
