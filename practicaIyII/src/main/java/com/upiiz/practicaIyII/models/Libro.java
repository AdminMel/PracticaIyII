package com.upiiz.practicaIyII.models;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String fecha;

    private Usuario usuario; // dueño del libro

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Usuario getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
