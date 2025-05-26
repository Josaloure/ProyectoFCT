package com.GestionMensajeriaCentro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class: Application
 * Description: Clase principal de la aplicación Spring Boot. Se encarga de iniciar la aplicación.
 * Date: 2025-05-01
 * @author Jose Alonso Ureña
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.GestionMensajeriaCentro")
public class Application {

    /**
     * Método principal que ejecuta la aplicación Spring Boot.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
