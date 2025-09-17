package com.upiiz.practicaIyII.services;

import com.upiiz.practicaIyII.models.Usuario;
import com.upiiz.practicaIyII.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsuarioService implements UsuarioRepository {
    private final List<Usuario> usuarios = new ArrayList<>();
    // email -> Usuario
    private final Map<String, Usuario> store = new ConcurrentHashMap<>();

    @Override
    public List<Usuario> findAll() {
        return List.copyOf(usuarios);
    }

    @Override
    public Usuario getUsuario(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(String name, String email, String password) {
        int id = usuarios.size();
        Usuario u = new Usuario(id, password, name, email);
        usuarios.add(u);
        store.put(email, u); // ¡clave para validar!
    }

    @Override
    public boolean validar(String email, String password) {
        Usuario u = store.get(email);
        return u != null && Objects.equals(u.getPassword(), password);
    }

    public Optional<Usuario> buscarPorUsername(String email) {
        return Optional.ofNullable(store.get(email));
    }

    @PostConstruct
    public void seed() {
            save("Admin", "admin@example.com", "admin123");
    }
}
