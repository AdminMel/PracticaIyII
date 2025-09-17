package com.upiiz.practicaIyII.repositories;
import com.upiiz.practicaIyII.models.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository {
    //Regrese todos los usuarios
    public List<Usuario> findAll();
    //Regrese un Usuario por ID
    public Usuario getUsuario(int id);
    //Agregar un Usuario
    public void save(String name, String email, String password);
    //Validar usuarios
    public boolean validar(String email, String password);
}
