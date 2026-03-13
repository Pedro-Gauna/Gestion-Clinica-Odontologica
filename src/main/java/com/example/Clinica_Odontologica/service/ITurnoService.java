package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.*;
import com.example.Clinica_Odontologica.model.Turno;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoService {

    public TurnoResponseDTO createTurno(Long id_odontologo, Long id_paciente, TurnoRequestDTO turnoDto);

    public List<TurnoResponseDTO>getTurnosActivos();

    public TurnoResponseDTO cancelTurno(Long id_turno, String motivo);

    public TurnoResponseDTO mapToDTO(Turno turno);

    public List<TurnoResponseDTO> listaTurnosFuturos(Long id_odontologo);

    public List<TurnoResponseDTO>listaTurnosPorDia(Long id_odontologo, LocalDate fecha);

    public List<TurnoResponseDTO> getTurnosOdontologo(Long id_odonto);

    public observacionTurnoDTO registrarObservacion(Long id_turno, RegistrarObservacionDTO reg);

    public void eliminarTurno(Long id_turno);

    public CantPacientesPorDiaDTO cantPacientePorDia(LocalDate fecha);

}
