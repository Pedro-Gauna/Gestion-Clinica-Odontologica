package com.example.Clinica_Odontologica.repository;

import com.example.Clinica_Odontologica.model.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IResponsableRepository extends JpaRepository<Responsable,Long> {
    List<Responsable>findByEstado(String estado);

    Optional<Responsable> findByIdAndEstado(Long id_resp,String estado);
}
