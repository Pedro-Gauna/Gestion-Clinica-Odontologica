package com.example.Clinica_Odontologica.service;


import com.example.Clinica_Odontologica.model.Usuario;
import com.example.Clinica_Odontologica.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Cargando usuario para autenticacion: " + username);

        Usuario usu = usuRepo.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado" + username));

        if(usu.getUsuario() == null || usu.getUsuario().isBlank()){
            System.out.println("Usuario encontrado pero campo 'usuario' es nulo/blank en la BD. Usuario id: " + usu.getId_usuario());
            throw new UsernameNotFoundException("Datos de usuario invalidos.");
        }

        //Normalizar el Rol para que empiece con ROLE_ADMIN,ROLE_ODONTOLOGO,ROLE_SECRETARIO.
        String rawRole = usu.getRol();
        String normalizedRol = (rawRole == null || rawRole.isBlank())
                ? "ROLE_USER"
                : (rawRole.startsWith("ROLE_") ? rawRole : "ROLE_" + rawRole.trim().toUpperCase());

        String pass = usu.getContrasenia();
        if(pass == null || pass.isBlank()){
            System.out.println("Usuario '{}' tiene contraseña nula o vacia en la BD" + username);
            throw new UsernameNotFoundException("Usuario invalido o contraseña no establecida.");
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(normalizedRol));
        System.out.println("Usuario cargado y authorities: " + usu.getUsuario() + authorities);

        return org.springframework.security.core.userdetails.User.builder()
                .username(usu.getUsuario())
                .password(pass)
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
