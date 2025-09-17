package com.upiiz.practicaIyII.config;

import com.upiiz.practicaIyII.componets.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") // protege todo…
                .excludePathPatterns(   // …excepto lo público:
                        // páginas de autenticación / error
                        "/login", "/register", "/logout", "/error", "/favicon.ico",
                        // recursos estáticos (según tu estructura)
                        "/css/**", "/js/**", "/assets.img/**",
                        // si usas webjars u otros
                        "/webjars/**"
                );
    }
}
