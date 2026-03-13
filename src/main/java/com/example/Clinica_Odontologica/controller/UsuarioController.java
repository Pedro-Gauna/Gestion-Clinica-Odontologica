package com.example.Clinica_Odontologica.controller;

import com.example.Clinica_Odontologica.dto.UsuarioRequestDTO;
import com.example.Clinica_Odontologica.dto.UsuarioResponseDTO;
import com.example.Clinica_Odontologica.dto.UsuarioUpdateDTO;
import com.example.Clinica_Odontologica.model.Usuario;
import com.example.Clinica_Odontologica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuService;

    //Solo los usuarios ADMIN pueden acceder a estos endpoints para manipular datos de usuarios. IMPORTANTE

    //Listar
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getUsuarios(){
        return ResponseEntity.ok(usuService.getUsuarios());
    }

    //Crear
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO>createUsuario(@RequestBody UsuarioRequestDTO usuDTO){
        UsuarioResponseDTO response = usuService.createUsuario(usuDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Editar
    @PutMapping("/{id_usuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>editUsuario(@PathVariable Long id_usuario, @RequestBody UsuarioUpdateDTO dto){
        usuService.editUsuario(id_usuario,dto);
        return ResponseEntity.ok("El usuario fue actualizado con exitó.");
    }

    //Buscar por id
    @GetMapping("/{id_usuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> findUsuario(@PathVariable Long id_usuario){
        Usuario usu = usuService.findUsuario(id_usuario);
        return new ResponseEntity<>(usu,HttpStatus.OK);
    }

    //Baja Logica
    @PutMapping("/baja/{id_usuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> bajaUsuario(@PathVariable Long id_usuario){
        usuService.bajaLogicaUsuario(id_usuario);
        return ResponseEntity.ok("El usuario fue dado de baja correctamente");
    }

    //Reactivar Usuario
    @PutMapping("/reactivar/{id_usuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> altaUsuario(@PathVariable Long id_usuario){
        usuService.altaLogicaUsuario(id_usuario);
        return ResponseEntity.ok("El usuario fue reactivado con exitó.");
    }
}
