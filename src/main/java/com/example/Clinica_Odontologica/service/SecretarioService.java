package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.SecretarioRequestDTO;
import com.example.Clinica_Odontologica.dto.SecretarioResponseDTO;
import com.example.Clinica_Odontologica.dto.SecretarioUpdateDTO;
import com.example.Clinica_Odontologica.model.Secretario;
import com.example.Clinica_Odontologica.model.Usuario;
import com.example.Clinica_Odontologica.repository.ISecretarioRepository;
import com.example.Clinica_Odontologica.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SecretarioService implements ISecretarioService{

    @Autowired
    private ISecretarioRepository secretarioRepo;

    @Autowired
    private IUsuarioRepository usuRepo;

    @Override
    public SecretarioResponseDTO createSecretario(SecretarioRequestDTO secretarioDTO){

        Usuario usu = usuRepo.findById(secretarioDTO.getId_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(!usu.getRol().equals("SECRETARIO")){
            throw new RuntimeException("El usuario no tiene asignado el Rol de Secretario");
        }

        if(usu.getUnSecretario() != null){
            throw new RuntimeException("El usuario ya tiene asociado un Secretario");
        }

        Secretario secre = new Secretario();
        secre.setDni(secretarioDTO.getDni());
        secre.setNombre(secretarioDTO.getNombre());
        secre.setApellido(secretarioDTO.getApellido());
        secre.setTelefono(secretarioDTO.getTelefono());
        secre.setDireccion(secretarioDTO.getDireccion());
        secre.setFecha_nac(secretarioDTO.getFecha_nac());
        secre.setSector(secretarioDTO.getSector());
        secre.setEstado("ACTIVO");
        secre.setUsuario(usu);

        Secretario nuevoSecretario = secretarioRepo.save(secre);

        return ResponseSecre(nuevoSecretario);

    }

    private SecretarioResponseDTO ResponseSecre(Secretario nuevoSecretario) {
        SecretarioResponseDTO secreDTO = new SecretarioResponseDTO();
        secreDTO.setDni(nuevoSecretario.getDni());
        secreDTO.setNombre(nuevoSecretario.getNombre());
        secreDTO.setApellido(nuevoSecretario.getApellido());
        secreDTO.setSector(nuevoSecretario.getSector());
        secreDTO.setEstado(nuevoSecretario.getEstado());

        return secreDTO;
    }

    @Override
    public List<Secretario> getSecretarios(){
        return secretarioRepo.findAll();
    }

    @Override
    public void editSecretario(Long id_secretario, SecretarioUpdateDTO secretarioDTO){
        Secretario secre = secretarioRepo.findById(id_secretario)
                .orElseThrow(() -> new RuntimeException("El usuario no fue encontrado."));

        secre.setDni(secretarioDTO.getDni());
        secre.setNombre(secretarioDTO.getNombre());
        secre.setApellido(secretarioDTO.getApellido());
        secre.setTelefono(secretarioDTO.getTelefono());
        secre.setDireccion(secretarioDTO.getDireccion());
        secre.setFecha_nac(secretarioDTO.getFecha_nac());
        secre.setSector(secretarioDTO.getSector());

        secretarioRepo.save(secre);
    }

    @Override
    public SecretarioResponseDTO bajaLogicaSecretario(Long id_secretario) {
        Secretario secretario = secretarioRepo.findByIdAndEstado(id_secretario, "ACTIVO")
                .orElseThrow(()-> new IllegalArgumentException("Secretario no encontrado"));
        secretario.setEstado("INACTIVO");
        Secretario nuevo = secretarioRepo.save(secretario);
        return ResponseSecre(nuevo);
    }

    @Override
    public SecretarioResponseDTO altaLogicaSecretario(Long id_secretario) {
        Secretario secretario = secretarioRepo.findById(id_secretario)
                .orElseThrow(()-> new IllegalArgumentException("Secretario no encontrado"));
        secretario.setEstado("ACTIVO");
        Secretario altaSecretario = secretarioRepo.save(secretario);
        return ResponseSecre(altaSecretario);
    }
}
