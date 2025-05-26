/**
 * Class: GeneradorClave
 * Description: Componente que se ejecuta al iniciar la aplicación para generar
 *              automáticamente usuarios ADMIN y PROFESOR si no existen.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.utiles;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.modelo.enums.Rol;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa CommandLineRunner para ejecutar código al arrancar la aplicación.
 * Se encarga de crear usuarios predeterminados (ADMIN y PROFESOR) si no existen.
 */
@Component
public class GeneradorClave implements CommandLineRunner {

    // Inyección del encoder para cifrar contraseñas
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Servicio para operaciones con usuarios
    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     * Crea un usuario administrador y uno profesor si aún no existen.
     */
    @Override
    public void run(String... args) {
        crearUsuarioAdmin();
        crearUsuarioProfesor();
    }

    /**
     * Método para crear un usuario con rol ADMIN por defecto si no existe.
     */
    private void crearUsuarioAdmin() {
        String email = "admin@centro.com";
        String password = "admin123";

        // Verifica si ya existe un usuario con ese email
        if (!usuarioServicio.existsByEmail(email)) {
            // Codifica la contraseña
            String hash = passwordEncoder.encode(password);

            // Construye el objeto Usuario ADMIN
            Usuario admin = Usuario.builder()
                    .email(email)
                    .clave(hash)
                    .nombre("Admin")
                    .apellidos("Sistema")
                    .nick("admin")
                    .rol(Rol.ADMIN)
                    .usuarioMovil(false)
                    .build();

            // Guarda el usuario en la base de datos
            usuarioServicio.save(admin);

            // Imprime en consola los datos del nuevo usuario
            System.out.println(" Usuario ADMIN creado:");
            System.out.println("   Email: " + email);
            System.out.println("   Contraseña: " + password);
        } else {
            System.out.println(" Usuario ADMIN ya existe: " + email);
        }
    }

    /**
     * Método para crear un usuario con rol PROFESOR por defecto si no existe.
     */
    private void crearUsuarioProfesor() {
        String email = "profesor@centro.com";
        String password = "prof123";

        // Verifica si ya existe un usuario con ese email
        if (!usuarioServicio.existsByEmail(email)) {
            // Codifica la contraseña
            String hash = passwordEncoder.encode(password);

            // Construye el objeto Usuario PROFESOR
            Usuario profesor = Usuario.builder()
                    .email(email)
                    .clave(hash)
                    .nombre("Pedro")
                    .apellidos("Gómez")
                    .nick("pgomez")
                    .rol(Rol.PROFESOR)
                    .usuarioMovil(true)
                    .build();

            // Guarda el usuario en la base de datos
            usuarioServicio.save(profesor);

            // Imprime en consola los datos del nuevo usuario
            System.out.println(" Usuario PROFESOR creado:");
            System.out.println("   Email: " + email);
            System.out.println("   Contraseña: " + password);
        } else {
            System.out.println(" Usuario PROFESOR ya existe: " + email);
        }
    }
}
