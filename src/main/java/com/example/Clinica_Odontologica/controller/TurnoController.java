package com.example.Clinica_Odontologica.controller;

import com.example.Clinica_Odontologica.dto.*;
import com.example.Clinica_Odontologica.service.ITurnoService;
import com.example.Clinica_Odontologica.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;

    @Autowired
    private IUsuarioService usuService;


    @PostMapping("/nuevoTurno/{id_odontologo}/{id_paciente}")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ODONTOLOGO')")
    public ResponseEntity<TurnoResponseDTO>createTurno( @PathVariable Long id_odontologo,
                                                        @PathVariable Long id_paciente,
                                                        @RequestBody TurnoRequestDTO turnoDto){

        TurnoResponseDTO nuevoTurno = turnoService.createTurno(id_odontologo,id_paciente,turnoDto);
        return new ResponseEntity<>(nuevoTurno, HttpStatus.CREATED);
    }

    @GetMapping("/reservados")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<TurnoResponseDTO>>getActiveTurnos(){
        return ResponseEntity.ok(turnoService.getTurnosActivos());
    }

    @PutMapping("/cancelar/{id_turno}")
    @PreAuthorize("hasRole('SECRETARIO')")
    public ResponseEntity<TurnoResponseDTO>cancelTurno(@PathVariable Long id_turno,
                                                        @RequestBody cancelarTurnoRequestDTO motivoCanc){
        TurnoResponseDTO turnoCancelado = turnoService.cancelTurno(id_turno,motivoCanc.getMotivoCancelacion());
        return ResponseEntity.ok(turnoCancelado);
    }

    @GetMapping("/turnosOdontologo/{id_odonto}")
    @PreAuthorize("hasRole('ODONTOLOGO') or hasRole('SECRETARIO')")
    public ResponseEntity<List<TurnoResponseDTO>>getTurnoOdontologo(@PathVariable Long id_odonto){
        return ResponseEntity.ok(turnoService.getTurnosOdontologo(id_odonto));
    }

    @GetMapping("/turnosFuturos/{id_odonto}")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ODONTOLOGO')")
    public ResponseEntity<List<TurnoResponseDTO>>getTurnosFuturos(@PathVariable Long id_odonto){
        return ResponseEntity.ok(turnoService.listaTurnosFuturos(id_odonto));
    }

    @GetMapping("/turnoPorDia/{id_odonto}")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ODONTOLOGO')")
    public ResponseEntity<List<TurnoResponseDTO>> getTurnosPorDia(
            @PathVariable Long id_odonto,
            @RequestParam LocalDate fecha){

        return ResponseEntity.ok(turnoService.listaTurnosPorDia(id_odonto,fecha));
    }

    @PutMapping("/observacion/{id_turno}")
    @PreAuthorize("hasRole('SECRETARIO') or hasRole('ODONTOLOGO')")
    public ResponseEntity<observacionTurnoDTO> registrarObservacion(
            @PathVariable Long id_turno,
            @RequestBody RegistrarObservacionDTO reg
            ){
        return ResponseEntity.ok(turnoService.registrarObservacion(id_turno,reg));
    }

    @GetMapping("/pacientesPorDia")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<CantPacientesPorDiaDTO>cantPacientesPorDia(@RequestParam LocalDate fecha){
        return ResponseEntity.ok(turnoService.cantPacientePorDia(fecha));
    }

}
