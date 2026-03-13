package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.EstadisticaOSDTO;
import com.example.Clinica_Odontologica.dto.PacienteRequestDTO;
import com.example.Clinica_Odontologica.dto.PacienteResponseDTO;
import com.example.Clinica_Odontologica.dto.PacienteUpdateDTO;
import com.example.Clinica_Odontologica.model.Paciente;
import com.example.Clinica_Odontologica.model.Responsable;
import com.example.Clinica_Odontologica.repository.IPacienteRepository;
import com.example.Clinica_Odontologica.repository.IResponsableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService implements IPacienteService{

    @Autowired
    private IPacienteRepository pacRepo;

    @Autowired
    private IResponsableRepository respRepo;

    @Override
    public PacienteResponseDTO createPaciente(PacienteRequestDTO pacDto){

        LocalDate ahora = LocalDate.now();
        int edad = Period.between(pacDto.getFecha_nac(),ahora).getYears();
        Responsable resp = null;

        if(edad < 18){
            if(pacDto.getId_responsable() == null) {
                throw new RuntimeException("El paciente es menor de edad y necesita un responsable");
            }
            resp =  respRepo.findById(pacDto.getId_responsable())
                    .orElseThrow(()-> new RuntimeException("El responsable no se encontro."));
        }

        Paciente pac = new Paciente();
        pac.setDni(pacDto.getDni());
        pac.setNombre(pacDto.getNombre());
        pac.setApellido(pacDto.getApellido());
        pac.setTelefono(pacDto.getTelefono());
        pac.setDireccion(pacDto.getDireccion());
        pac.setFecha_nac(pacDto.getFecha_nac());
        pac.setTieneOS(pacDto.isTiene_OS());
        pac.setTipo_tratamiento(pacDto.getTipo_tratamiento());
        pac.setUnResponsable(resp);
        pac.setEstado("ACTIVO");

        Paciente nuevoPac = pacRepo.save(pac);

        return ResponsePac(nuevoPac);
    }

    private PacienteResponseDTO ResponsePac(Paciente nuevoPac) {

        PacienteResponseDTO pacDto = new PacienteResponseDTO();
        pacDto.setDni(nuevoPac.getDni());
        pacDto.setNombre(nuevoPac.getNombre());
        pacDto.setApellido(nuevoPac.getApellido());
        pacDto.setFecha_nac(nuevoPac.getFecha_nac());
        pacDto.setEstado(nuevoPac.getEstado());
        pacDto.setTiene_OS(nuevoPac.isTieneOS());
        pacDto.setTipo_tratamiento(nuevoPac.getTipo_tratamiento());
        if(nuevoPac.getUnResponsable() != null){
            pacDto.setId_responsable(nuevoPac.getUnResponsable().getId());
        } else {
            pacDto.setId_responsable(null);
        }

        return pacDto;
    }

    @Override
    public List<PacienteResponseDTO> getPacientesActivos() {
        List<Paciente> activos = pacRepo.findByEstado("ACTIVO");
        List<PacienteResponseDTO> pacDTO = new ArrayList<>();

        for(Paciente pac: activos){
            pacDTO.add(ResponsePac(pac));
        }
        return pacDTO;
    }

    @Override
    public PacienteResponseDTO getPacienteActivo(Long id_pac) {
        Paciente pac = pacRepo.findByIdAndEstado(id_pac, "ACTIVO")
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado o esta inactivo"));
        PacienteResponseDTO nuevoPac = ResponsePac(pac);
        return nuevoPac;
    }


    @Override
    public PacienteUpdateDTO editPaciente(Long id_pac,PacienteRequestDTO pacDto){

        Paciente pac = pacRepo.findById(id_pac)
                        .orElseThrow(()-> new RuntimeException("El paciente no fue encontrado"));

            pac.setDni(pacDto.getDni());
            pac.setNombre(pacDto.getNombre());
            pac.setApellido(pacDto.getApellido());
            pac.setTelefono(pacDto.getTelefono());
            pac.setDireccion(pacDto.getDireccion());
            pac.setFecha_nac(pacDto.getFecha_nac());
            pac.setTieneOS(pacDto.isTiene_OS());
            pac.setTipo_tratamiento(pacDto.getTipo_tratamiento());

            LocalDate ahora = LocalDate.now();
            int edad = Period.between(pacDto.getFecha_nac(),ahora).getYears();

            if(pacDto.getId_responsable() != null){
                Responsable resp = respRepo.findById(pacDto.getId_responsable())
                        .orElseThrow(()-> new RuntimeException("El responsable no fue encontrado."));
                pac.setUnResponsable(resp);
            }else{
                pac.setUnResponsable(null);
            }

            if(edad < 18 && pac.getUnResponsable() == null){
                throw new RuntimeException("Un paciente menor de edad debe tener un responsable a cargo");
            }

            Paciente nuevoPac = pacRepo.save(pac);
            return ResponseUpdate(nuevoPac);
    }

    private PacienteUpdateDTO ResponseUpdate(Paciente nuevoPac) {
        PacienteUpdateDTO pacDto = new PacienteUpdateDTO();
        pacDto.setDni(nuevoPac.getDni());
        pacDto.setNombre(nuevoPac.getNombre());
        pacDto.setApellido(nuevoPac.getApellido());
        pacDto.setTelefono(nuevoPac.getTelefono());
        pacDto.setDireccion(nuevoPac.getDireccion());
        pacDto.setFecha_nac(nuevoPac.getFecha_nac());
        pacDto.setEstado(nuevoPac.getEstado());
        pacDto.setTiene_OS(nuevoPac.isTieneOS());
        pacDto.setTipo_tratamiento(nuevoPac.getTipo_tratamiento());

        if(pacDto.getId_responsable() != null){
            pacDto.setId_responsable(nuevoPac.getUnResponsable().getId());
        }else{
            pacDto.setId_responsable(null);
        }
        return pacDto;
    }

    @Override
    public List<PacienteResponseDTO> getPacientesAdmin() {
        List<Paciente> pacientes = pacRepo.findAll();
        List<PacienteResponseDTO> dtos = new ArrayList<>();

        for(Paciente pac: pacientes){
            dtos.add(ResponsePac(pac));
        }
        return dtos;
    }

    @Override
    public List<Paciente> listaPacientesPorOdonto(Long id_odontologo) {
        return pacRepo.findByListaTurnosUnOdontologoId(id_odontologo);
    }

    @Override
    public PacienteResponseDTO bajaLogicaPaciente(Long id_paciente) {
        Paciente pac = pacRepo.findByIdAndEstado(id_paciente,"ACTIVO")
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        pac.setEstado("INACTIVO");
        Paciente pacienteResp = pacRepo.save(pac);
        return ResponsePac(pacienteResp);
    }

    @Override
    public PacienteResponseDTO altaLogicaPaciente(Long id_paciente) {
        Paciente pac = pacRepo.findById(id_paciente)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        pac.setEstado("ACTIVO");
        Paciente pacienteResp = pacRepo.save(pac);
        return ResponsePac(pacienteResp);
    }

    @Override
    public void deletePaciente(Long id_paciente) {
        Paciente pac = pacRepo.findById(id_paciente)
                .orElseThrow(() -> new IllegalArgumentException("El paciente no existe"));

        if(!pac.getListaTurnos().isEmpty()){
            throw new IllegalArgumentException("No se puede eliminar ya que tiene turnos asociados");
        }
        pacRepo.delete(pac);
    }

    @Override
    public EstadisticaOSDTO cantPacientesConOS(){
        int conOS = pacRepo.countByTieneOSTrue();
        int sinOS = pacRepo.countByTieneOSFalse();
        return new EstadisticaOSDTO(conOS,sinOS);
    }

}
