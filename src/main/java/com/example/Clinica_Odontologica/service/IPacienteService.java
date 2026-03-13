package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.EstadisticaOSDTO;
import com.example.Clinica_Odontologica.dto.PacienteRequestDTO;
import com.example.Clinica_Odontologica.dto.PacienteResponseDTO;
import com.example.Clinica_Odontologica.dto.PacienteUpdateDTO;
import com.example.Clinica_Odontologica.model.Paciente;

import java.util.List;

public interface IPacienteService {

    public PacienteResponseDTO createPaciente(PacienteRequestDTO pacDto);

    public List<PacienteResponseDTO> getPacientesActivos();

    public PacienteResponseDTO getPacienteActivo(Long id_pac);

    public PacienteUpdateDTO editPaciente(Long id_pac, PacienteRequestDTO pacDto);

    public List<PacienteResponseDTO> getPacientesAdmin();

    public List<Paciente> listaPacientesPorOdonto(Long id_odontologo);

    public PacienteResponseDTO bajaLogicaPaciente(Long id_paciente);

    public PacienteResponseDTO altaLogicaPaciente(Long id_paciente);

    public void deletePaciente(Long id_paciente);


    public EstadisticaOSDTO cantPacientesConOS();
}
