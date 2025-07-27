package com.grupo4.restaurante.config;

import com.grupo4.restaurante.services.JpaUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad para proteger rutas según rol.
 * Incluye autenticación con usuarios en memoria y login personalizado.
 *
 * @author David De La Puente - KirgoDev -
 * @Since 2025-06-23
 */
@Configuration
@AllArgsConstructor
@EnableMethodSecurity // Para usar @preAuthorize en controladores si es necesario-
public class SecurityConfig {

    private final JpaUserDetailService jpaUserDetailService;

    /*
     * Codificador de contraseñas usando Bcrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Configurar los permisos de acceso según rutas y roles-
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(jpaUserDetailService)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/home", "/contacto/**", "/auth/registro", "/error/**", "/login", "/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                .requestMatchers("/productos/**", "empleados/**").hasAnyRole("ADMIN", "ENCARGADO")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll()
                ).logout(logout -> logout.logoutSuccessUrl("/").permitAll()
                );
        return http.build();
    }


}
