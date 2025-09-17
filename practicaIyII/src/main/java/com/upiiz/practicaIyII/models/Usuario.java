package com.upiiz.practicaIyII.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String password;
    private String name;
    private String email;
    private List<Libro> libros = new ArrayList<>(); // Relación

    public Usuario() {
    }

    public Usuario(int id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }
}
