package com.example.Clinica_Odontologica.controller;

import com.example.Clinica_Odontologica.dto.OdontologoRequestDTO;
import com.example.Clinica_Odontologica.dto.OdontologoResponseDTO;
import com.example.Clinica_Odontologica.dto.OdontologoUpdateDTO;
import com.example.Clinica_Odontologica.model.Odontologo;
import com.example.Clinica_Odontologica.model.Usuario;
import com.example.Clinica_Odontologica.repository.IUsuarioRepository;
import com.example.Clinica_Odontologica.service.IOdontologoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/odontologos")
public class OdontologoController {

    @Autowired
    private IUsuarioRepository usuRepo;

    @Autowired
    private IOdontologoService odontoService;

    @PostMapping
    public ResponseEntity<OdontologoResponseDTO> createOdontologo(@RequestBody OdontologoRequestDTO odontoDTO){
        OdontologoResponseDTO nuevoOdonto = odontoService.createOdontologo(odontoDTO);
        return new ResponseEntity<>(nuevoOdonto,HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<List<OdontologoResponseDTO>>getOdontologos(){
            return ResponseEntity.ok(odontoService.getOdontologosActivos());
    }

    @GetMapping("/{id_odonto}")
    public ResponseEntity<OdontologoResponseDTO> getOdontologo(@PathVariable Long id_odonto) {
        return ResponseEntity.ok(odontoService.getOdontologoActivo(id_odonto));
    }

    @PutMapping("/editar/{id_odonto}")
    public ResponseEntity<OdontologoUpdateDTO> editOdontologo(@PathVariable Long id_odonto,@RequestBody OdontologoRequestDTO odontoDto){
        OdontologoUpdateDTO nuevoOdonto = odontoService.editOdontologo(id_odonto,odontoDto);
        return new ResponseEntity<>(nuevoOdonto,HttpStatus.OK);
    }

    //Baja Logica "ACTIVO"/"INACTIVO"
    @PutMapping("/baja/{id_odonto}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OdontologoResponseDTO> bajaOdontologo(@PathVariable Long id_odonto){
        OdontologoResponseDTO odonto = odontoService.bajaLogicaOdontologo(id_odonto);
        return new ResponseEntity<>(odonto,HttpStatus.OK);
    }

    //Alta logica "ACTIVO"/"INACTIVO"
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reactivar/{id_odonto}")
    public ResponseEntity<OdontologoResponseDTO>altaOdontologo(@PathVariable Long id_odonto){
        OdontologoResponseDTO odontoDto = odontoService.altaLogicaOdontologo(id_odonto);
        return new ResponseEntity<>(odontoDto, HttpStatus.OK);
    }

}
