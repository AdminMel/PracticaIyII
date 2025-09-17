package com.upiiz.practicaIyII.controllers;

import com.upiiz.practicaIyII.models.Libro;
import com.upiiz.practicaIyII.models.Usuario;
import com.upiiz.practicaIyII.services.LibroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LibrosController {

    private static final String SESSION_USER = "AUTH_USER";
    private final LibroService libroService;

    public LibrosController(LibroService libroService) {
        this.libroService = libroService;
    }

    // LISTAR (home)  GET "/"
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        Usuario u = getUser(session);
        if (u == null) return "redirect:/login";
        model.addAttribute("usuario", u);
        model.addAttribute("libros", libroService.findAllByUser(u.getEmail()));
        model.addAttribute("libroForm", new Libro()); // opcional: form inline en index
        return "index";
    }

    @GetMapping("/eliminar/{id}")
    public String detalle(@PathVariable int id, Model model, HttpSession session) {
        Usuario u = getUser(session);
        if (u == null) return "redirect:/login";
        Libro l = libroService.findById(u.getEmail(), id);
        if (l == null) return "redirect:/";
        model.addAttribute("usuario", u);
        model.addAttribute("libro", l);
        return "eliminar";
    }

    // NUEVO (form)  GET "/libros/nuevo"
    @GetMapping("/agregar")
    public String nuevo(Model model, HttpSession session) {
        Usuario u = getUser(session);
        if (u == null) return "redirect:/login";
        model.addAttribute("usuario", u);
        model.addAttribute("libro", new Libro());
        return "agregar"; // tu vista para crear
    }

    // CREAR (acción)  POST "/libros"
    @PostMapping("/agregar")
    public String crear(@ModelAttribute("libro") Libro libro,
                        HttpSession session,
                        RedirectAttributes ra) {
        Usuario u = getUser(session);
        if (u == null) return "redirect:/login";
        libroService.save(u.getEmail(), libro);
        ra.addFlashAttribute("ok", "Libro creado");
        return "redirect:/";
    }

    // EDITAR (form)  GET "/libros/{id}/editar"
    @GetMapping("/actualizar/{id}")
    public String editar(@PathVariable int id, Model model, HttpSession session) {
        Usuario u = getUser(session);
        if (u == null) return "redirect:/login";
        Libro l = libroService.findById(u.getEmail(), id);
        if (l == null) return "redirect:/";
        model.addAttribute("usuario", u);
        model.addAttribute("libro", l);
        return "actualizar"; // tu vista para editar
    }

    // ACTUALIZAR (acción)  POST "/libros/{id}/actualizar"
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute("libro") Libro libro,
                             HttpSession session,
                             RedirectAttributes ra) {
        Usuario u = getUser(session);
        if (u == null) return "redirect:/login";
        libro.setId(libro.getId());
        libroService.update(u.getEmail(), libro);
        ra.addFlashAttribute("ok", "Libro actualizado");
        return "redirect:/";
    }

    // ELIMINAR (acción)  POST "/libros/{id}/eliminar"
    @PostMapping("/eliminar")
    public String eliminar(@ModelAttribute("libro") Libro libro, HttpSession session, RedirectAttributes ra) {
        Usuario u = getUser(session);
        if (u == null) return "redirect:/login";
        libroService.delete(u.getEmail(), libro.getId());
        ra.addFlashAttribute("ok", "Libro eliminado");
        return "redirect:/";
    }

    private Usuario getUser(HttpSession session) {
        return (Usuario) session.getAttribute(SESSION_USER);
    }
}
