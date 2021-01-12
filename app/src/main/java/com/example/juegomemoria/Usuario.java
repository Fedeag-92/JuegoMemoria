package com.example.juegomemoria;

public class Usuario {
    public String nombre;
    public String apellido;
    public String username;
    public String password;

    public Usuario(String nombre, String apellido, String user, String pass) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = user;
        this.password = pass;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
