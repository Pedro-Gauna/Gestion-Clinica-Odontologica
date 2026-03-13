package com.example.Clinica_Odontologica.repository;

import com.example.Clinica_Odontologica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);

}
