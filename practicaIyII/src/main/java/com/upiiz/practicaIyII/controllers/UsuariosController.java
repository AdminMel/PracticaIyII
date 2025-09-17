package com.upiiz.practicaIyII.controllers;

import com.upiiz.practicaIyII.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuariosController {

    public static final String SESSION_USER = "AUTH_USER";
    private final UsuarioService usuarios;

    public UsuariosController(UsuarioService usuarios) {
        this.usuarios = usuarios;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        return "examples/login"; // sin "/" al inicio
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session) {
        if (usuarios.validar(email, password)) {
            usuarios.buscarPorUsername(email)
                    .ifPresent(u -> session.setAttribute(SESSION_USER, u));
            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerForm(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        return "examples/register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String email,
                             @RequestParam String name,
                             @RequestParam String password) {
        usuarios.save(name, email, password);
        return "redirect:/login";
    }
}
