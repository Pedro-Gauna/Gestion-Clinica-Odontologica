package com.example.Clinica_Odontologica.controller;

import com.example.Clinica_Odontologica.dto.*;
import com.example.Clinica_Odontologica.model.Odontologo;
import com.example.Clinica_Odontologica.model.Paciente;
import com.example.Clinica_Odontologica.model.Usuario;
import com.example.Clinica_Odontologica.repository.IUsuarioRepository;
import com.example.Clinica_Odontologica.service.IOdontologoService;
import com.example.Clinica_Odontologica.service.IPacienteService;
import com.example.Clinica_Odontologica.service.SecretarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/secretarios")
public class SecretarioController {

    @Autowired
    private SecretarioService secreService;

    @Autowired
    private IPacienteService pacService;

    @Autowired
    private IOdontologoService odontoService;

    @Autowired
    private IUsuarioRepository usuRepo;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SecretarioResponseDTO> createSecretario(@RequestBody SecretarioRequestDTO secretarioDto){
            SecretarioResponseDTO nuevoSecretario = secreService.createSecretario(secretarioDto);
            return new ResponseEntity<>(nuevoSecretario, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarSecretarios(Authentication authentication){

        if(authentication == null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).body("El usuario no esta autenticado");
        }

        String username = authentication.getName();

        Usuario usu = usuRepo.findByUsuario(username)
                .orElseThrow(() -> new IllegalArgumentException("No se encontro este usuario"));

        String rol = usu.getRol().toUpperCase();

        //SOLO ADMIN
        if(rol.equals("ADMIN")){
            return ResponseEntity.ok(secreService.getSecretarios());
        }
        return ResponseEntity.status(403).body("Acceso denegado");

    }

    @PutMapping("editar/{id_secretario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>editSecretario(@PathVariable Long id_secretario,@RequestBody SecretarioUpdateDTO dto){
        secreService.editSecretario(id_secretario,dto);
        return ResponseEntity.ok("El usuario fue editado con exito.");
    }

    @PutMapping("/baja/{id_secretario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SecretarioResponseDTO> bajaLogicaSecretario(@PathVariable Long id_secretario){
        SecretarioResponseDTO secretario = secreService.bajaLogicaSecretario(id_secretario);
        return new ResponseEntity<>(secretario, HttpStatus.ACCEPTED);
    }

    @PutMapping("/reactivar/{id_secretario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SecretarioResponseDTO> altaLogicaSecretario(@PathVariable Long id_secretario){
        SecretarioResponseDTO secretario = secreService.altaLogicaSecretario(id_secretario);
        return new ResponseEntity<>(secretario, HttpStatus.ACCEPTED);
    }

    @GetMapping("/pacientes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<List<PacienteResponseDTO>>getPacientes(){
        return ResponseEntity.ok(pacService.getPacientesActivos());
    }

    @GetMapping("/odontologos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<List<OdontologoResponseDTO>>getOdontologos(){
        return ResponseEntity.ok(odontoService.getOdontologosActivos());
    }




}
