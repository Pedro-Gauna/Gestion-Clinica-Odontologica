package com.example.Clinica_Odontologica.repository;

import com.example.Clinica_Odontologica.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo,Long> {
    List<Odontologo> findByEstado(String estado);

    Optional<Odontologo> findByIdAndEstado(Long id, String estado);
}
