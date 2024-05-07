package edu.pucmm.message_passing.example1;

public class Persona {
    private String Nombre;
    private String Apellido;
    private int edad;

    public Persona(String nombre, int edad, String apellido) {
        Nombre = nombre;
        this.edad = edad;
        Apellido = apellido;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
