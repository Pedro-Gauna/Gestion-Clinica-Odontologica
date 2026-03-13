package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.UsuarioRequestDTO;
import com.example.Clinica_Odontologica.dto.UsuarioResponseDTO;
import com.example.Clinica_Odontologica.dto.UsuarioUpdateDTO;
import com.example.Clinica_Odontologica.model.Usuario;

import java.util.List;


public interface IUsuarioService {


    public UsuarioResponseDTO createUsuario(UsuarioRequestDTO usuDTO);

    public Usuario findUser(String usuario);

    public List<Usuario> getUsuarios();

    public void bajaLogicaUsuario(Long id_usuario);

    public void altaLogicaUsuario(Long id_usuario);

    public Usuario findUsuario(Long id_usuario);

    public void editUsuario(Long idUsuario, UsuarioUpdateDTO dto);
}
