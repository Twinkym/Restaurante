package com.grupo4.restaurante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad para proteger rutas según rol.
 * Incluye autenticación con usuarios en memoria y login personalizado.
 *
 * @author David De La Puente - KirgoDev -
 * @Since 2025-06-23
 */
@Configuration
@EnableMethodSecurity // Para usar @preAuthorize en controladores si es necesario-
public class SecurityConfig {

    /*
     * Codificador de contraseñas usando Bcrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    // Usuarios en memoria para pruebas iniciales.
    @Beanpublic
    UserDetailsService userDetailsService(BCryptPasswordEncoder encoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(

    }
    /*
     * Configurar los permisos de acceso según rutas y roles-
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/login", "/error", "/reservas/**", "/menus/**", "/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                .requestMatchers("/productos/**", "empleados/**").hasAnyRole("ADMIN", "ENCARGADO")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll()
                ).logout(logout -> logout.logoutSuccessUrl("/").permitAll()
                );
        return http.build();
    }


}
