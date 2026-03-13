package com.example.Clinica_Odontologica.repository;

import com.example.Clinica_Odontologica.model.Secretario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISecretarioRepository extends JpaRepository<Secretario, Long> {
    Optional<Secretario> findByIdAndEstado(Long id, String estado);
}
