package com.upiiz.practicaIyII.componets;

import com.upiiz.practicaIyII.controllers.UsuariosController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // Permitir preflight CORS / herramientas
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // ¿Hay sesión con usuario?
        Object user = request.getSession(false) == null ? null
                : request.getSession(false).getAttribute(UsuariosController.SESSION_USER);

        if (user != null) {
            return true; // autenticado
        }

        // No autenticado: redirige a /login
        response.sendRedirect("/login");
        return false;
    }
}
