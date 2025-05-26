/**
 * Class: CustomerDetailService
 * Description: Servicio de implementación de UserDetailsService para la autenticación de usuarios
 *              mediante su email, utilizado por Spring Security.
 * Date: 2025-05-23
 * Author: Jose Alonso Ureña
 */
package com.GestionMensajeriaCentro.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.GestionMensajeriaCentro.modelo.Usuario;
import com.GestionMensajeriaCentro.servicio.UsuarioServicio;

/**
 * Implementación de {@link UserDetailsService} que permite cargar detalles
 * del usuario por su dirección de correo electrónico. Es utilizada por
 * Spring Security durante el proceso de autenticación.
 */
@Service
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Carga los detalles del usuario utilizando su email como identificador.
     *
     * @param email el email del usuario
     * @return UserDetails objeto con la información necesaria para la autenticación
     * @throws UsernameNotFoundException si no se encuentra un usuario con el email proporcionado
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioServicio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getClave(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))
        );
    }
}
