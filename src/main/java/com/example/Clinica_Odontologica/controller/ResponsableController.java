package com.example.Clinica_Odontologica.controller;

import com.example.Clinica_Odontologica.dto.ResponsableRequestDTO;
import com.example.Clinica_Odontologica.dto.ResponsableResponseDTO;
import com.example.Clinica_Odontologica.service.IResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsable")
public class ResponsableController {

    @Autowired
    private IResponsableService respoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<ResponsableResponseDTO>createResponsable(@RequestBody ResponsableRequestDTO respDto){
        ResponsableResponseDTO nuevoResp = respoService.createResponsable(respDto);
        return new ResponseEntity<>(nuevoResp, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponsableResponseDTO>>getAllResponsablesAdmin(){
        return ResponseEntity.ok(respoService.getAllResponsablesAdmin());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<List<ResponsableResponseDTO>>getResponsablesActivos(){
        return ResponseEntity.ok(respoService.getResponsablesActivos());
    }

    @PutMapping("/{id_resp}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<ResponsableResponseDTO> editResponsable(@PathVariable Long id_resp, @RequestBody ResponsableRequestDTO dtoResp){
        ResponsableResponseDTO nuevoDto = respoService.editResponsable(id_resp,dtoResp);
        return new ResponseEntity<>(nuevoDto, HttpStatus.OK);
    }

    @PutMapping("/baja/{id_resp}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<ResponsableResponseDTO>bajaLogicaResponsable(@PathVariable Long id_resp){
        return ResponseEntity.ok(respoService.bajaLogicaResponsable(id_resp));
    }

    @PutMapping("/reactivar/{id_resp}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<ResponsableResponseDTO>altaLogicaResponsable(@PathVariable Long id_resp){
        return ResponseEntity.ok(respoService.altaLogicaResponsable(id_resp));
    }

    @DeleteMapping("/eliminar/{id_resp}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARIO')")
    public ResponseEntity<String>deleteResponsable(@PathVariable Long id_resp){
        respoService.deleteResponsable(id_resp);
        return ResponseEntity.ok("El responsable ha sido eliminado con exito.");
    }


}
