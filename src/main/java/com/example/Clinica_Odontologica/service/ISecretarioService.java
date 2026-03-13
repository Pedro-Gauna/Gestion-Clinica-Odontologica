package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.SecretarioRequestDTO;
import com.example.Clinica_Odontologica.dto.SecretarioResponseDTO;
import com.example.Clinica_Odontologica.dto.SecretarioUpdateDTO;
import com.example.Clinica_Odontologica.model.Secretario;

import java.util.List;

public interface ISecretarioService {

    public SecretarioResponseDTO createSecretario(SecretarioRequestDTO secretarioDTO);

    public List<Secretario> getSecretarios();

    public void editSecretario(Long id_secretario ,SecretarioUpdateDTO secretarioDTO);

    public SecretarioResponseDTO bajaLogicaSecretario(Long id_secretario);

    public SecretarioResponseDTO altaLogicaSecretario(Long id_secretario);
}
