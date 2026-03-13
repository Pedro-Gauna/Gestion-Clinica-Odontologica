package com.example.Clinica_Odontologica.repository;

import com.example.Clinica_Odontologica.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long>{

    List<Turno> findByEstado(String estado);

    Optional<Turno>findByUnOdontologoIdAndFechaTurnoAndHoraTurno(Long odontologoId,LocalDate fechaTurno, LocalTime horaTurno);

    //Traer todos los turnos de un Odontologo
    List<Turno>findByUnOdontologoId(Long id_odontologo);

    //Traer los turnos desde el dia presente hacia el futuro de un Odontologo
    List<Turno>findByUnOdontologoIdAndFechaTurnoGreaterThanEqual(Long id_odontologo, LocalDate fecha);

    List<Turno> findByUnOdontologoIdAndFechaTurno(Long id_odontologo, LocalDate fecha);

    int countByFechaTurnoAndEstado(LocalDate fecha, String estado);

}
