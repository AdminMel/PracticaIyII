package com.upiiz.practicaIyII.repositories;

import com.upiiz.practicaIyII.models.Libro;
import com.upiiz.practicaIyII.models.Usuario;

import java.util.List;

public interface LibroRepository    {
    List<Libro> findAllByUser(String userEmail);
    Libro findById(String userEmail, int id);
    void save(String userEmail, Libro libro);
    Libro update(String userEmail, Libro libro);
    void delete(String userEmail, int id);
}
