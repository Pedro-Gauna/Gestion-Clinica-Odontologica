package com.example.Clinica_Odontologica.repository;

import com.example.Clinica_Odontologica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IPacienteRepository extends JpaRepository<Paciente,Long>{

    List<Paciente>findByListaTurnosUnOdontologoId(Long id_odontologo);

    List<Paciente> findByEstado(String estado);

    Optional<Paciente> findByIdAndEstado(Long id, String estado);

    int countByTieneOSTrue();

    int countByTieneOSFalse();



}
