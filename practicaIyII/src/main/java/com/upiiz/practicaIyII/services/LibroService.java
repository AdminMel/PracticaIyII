package com.upiiz.practicaIyII.services;

import com.upiiz.practicaIyII.models.Libro;
import com.upiiz.practicaIyII.repositories.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LibroService implements LibroRepository {

    // email -> lista de libros del usuario
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<Libro>> store = new ConcurrentHashMap<>();
    // email -> contador de ids por usuario
    private final ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    @Override
    public List<Libro> findAllByUser(String userEmail) {
        return List.copyOf(store.getOrDefault(userEmail, new CopyOnWriteArrayList<>()));
    }

    @Override
    public Libro findById(String userEmail, int id) {
        return store.getOrDefault(userEmail, new CopyOnWriteArrayList<>())
                .stream().filter(l -> l.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(String userEmail, Libro libro) {
        Objects.requireNonNull(userEmail, "userEmail no puede ser null");
        Objects.requireNonNull(libro, "libro no puede ser null");
        counters.putIfAbsent(userEmail, new AtomicInteger(0));
        int nextId = counters.get(userEmail).incrementAndGet();
        libro.setId(nextId);
        store.computeIfAbsent(userEmail, k -> new CopyOnWriteArrayList<>()).add(libro);
    }

    @Override
    public Libro update(String userEmail, Libro libro) {
        Objects.requireNonNull(userEmail);
        Objects.requireNonNull(libro);
        var list = store.getOrDefault(userEmail, new CopyOnWriteArrayList<>());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == libro.getId()) {
                list.set(i, libro);
                return libro;
            }
        }
        return null;
    }

    @Override
    public void delete(String userEmail, int id) {
        store.computeIfPresent(userEmail, (k, list) -> {
            list.removeIf(l -> l.getId() == id);
            return list;
        });
    }
}
