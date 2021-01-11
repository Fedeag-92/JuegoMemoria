package com.example.juegomemoria.entidades;

public class Usuario {

    private String username;
    private String nombre;
    private String apellido;
    private String password;

    public Usuario(String username, String nombre, String apellido, String password){
        this.username=username;
        this.nombre=nombre;
        this.apellido=apellido;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
