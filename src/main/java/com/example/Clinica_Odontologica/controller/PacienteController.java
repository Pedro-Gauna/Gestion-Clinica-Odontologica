package com.example.Clinica_Odontologica.controller;

import com.example.Clinica_Odontologica.dto.EstadisticaOSDTO;
import com.example.Clinica_Odontologica.dto.PacienteRequestDTO;
import com.example.Clinica_Odontologica.dto.PacienteResponseDTO;
import com.example.Clinica_Odontologica.dto.PacienteUpdateDTO;
import com.example.Clinica_Odontologica.repository.IUsuarioRepository;
import com.example.Clinica_Odontologica.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteService pacService;

    @Autowired
    private IUsuarioRepository usuRepo;

    @PostMapping()
    public ResponseEntity<PacienteResponseDTO> createPaciente(@RequestBody PacienteRequestDTO pacienteDto){
        PacienteResponseDTO nuevoPac = pacService.createPaciente(pacienteDto);
        return new ResponseEntity<>(nuevoPac, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ODONTOLOGO') or hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<List<PacienteResponseDTO>>getPacientes(){
            return ResponseEntity.ok(pacService.getPacientesActivos());
    }

    @GetMapping("/buscar/{id_paciente}")
    public ResponseEntity<PacienteResponseDTO>getPaciente(@PathVariable Long id_paciente){
        return ResponseEntity.ok(pacService.getPacienteActivo(id_paciente));
    }

    @PutMapping("/editar/{id_pac}")
    public ResponseEntity<PacienteUpdateDTO> editPaciente(@PathVariable Long id_pac, @RequestBody PacienteRequestDTO pacDto){
        PacienteUpdateDTO nuevoPaciente = pacService.editPaciente(id_pac, pacDto);
        return new ResponseEntity<>(nuevoPaciente,HttpStatus.OK);
    }


    //Baja Logica.
    @PutMapping("/baja/{id_pac}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<PacienteResponseDTO> bajaLogicaPaciente(@PathVariable Long id_pac){
        PacienteResponseDTO paciente = pacService.bajaLogicaPaciente(id_pac);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    //Alta Logica.
    @PutMapping("/reactivar/{id_pac}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PacienteResponseDTO> altaLogicaPaciente(@PathVariable Long id_pac){
        PacienteResponseDTO paciente = pacService.altaLogicaPaciente(id_pac);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    //Baja Fisica.
    @DeleteMapping("/{id_pac}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePaciente(@PathVariable Long id_pac){
        pacService.deletePaciente(id_pac);
        return ResponseEntity.ok("Paciente eliminado con exitó");
    }

    @GetMapping("/admin/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PacienteResponseDTO>>allPacientes(){
            return ResponseEntity.ok(pacService.getPacientesAdmin());
    }

    @GetMapping("/EstadisticaObraSocial")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<EstadisticaOSDTO>cantPacientesConOS(){
        return ResponseEntity.ok(pacService.cantPacientesConOS());
    }


}
