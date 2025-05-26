/**
 * Class: WebSecurityConfig
 * Description: Configuración principal de seguridad de Spring Security. Define políticas de acceso, login, logout y codificación de contraseñas.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad de Spring Security. Define las reglas de autorización,
 * la configuración del formulario de login, logout, y el encoder de contraseñas.
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Bean que define el encoder de contraseñas. Se utiliza BCrypt para asegurar las claves.
     *
     * @return una instancia de BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean que expone el AuthenticationManager necesario para la autenticación.
     *
     * @param config configuración de autenticación inyectada por Spring
     * @return instancia de AuthenticationManager
     * @throws Exception si hay error al obtenerlo
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configuración de la cadena de filtros de seguridad.
     * Se definen rutas públicas, roles requeridos para acceder a recursos,
     * comportamiento del formulario de login y logout.
     *
     * @param http configuración del objeto HttpSecurity
     * @return la SecurityFilterChain construida
     * @throws Exception si ocurre un error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().and() // CSRF habilitado por defecto (buena práctica en apps web con formularios)
                .authorizeRequests()
                // Rutas accesibles públicamente
                .antMatchers("/login", "/css/**", "/js/**", "/img/**").permitAll()
                // Rutas solo accesibles para usuarios con rol ADMIN
                .antMatchers("/admin/**", "/api/usuarios/**").hasRole("ADMIN")
                // Rutas accesibles solo para PROFESORES
                .antMatchers("/api/profesor/**").hasRole("PROFESOR")
                // Historial accesible por ambos roles
                .antMatchers("/historialCorreos/**").hasAnyRole("ADMIN", "PROFESOR")
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/indice", true)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true") // Redirección tras logout
                .permitAll();

        return http.build();
    }
}
