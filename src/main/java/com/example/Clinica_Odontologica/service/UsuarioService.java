package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.UsuarioRequestDTO;
import com.example.Clinica_Odontologica.dto.UsuarioResponseDTO;
import com.example.Clinica_Odontologica.dto.UsuarioUpdateDTO;
import com.example.Clinica_Odontologica.model.Usuario;
import com.example.Clinica_Odontologica.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO createUsuario(UsuarioRequestDTO usuDTO) {

        Usuario usu = new Usuario();
        usu.setUsuario(usuDTO.getUsuario());
        usu.setRol(usuDTO.getRol());
        usu.setEstado("ACTIVO");

        //CLAVE CIFRADA
        usu.setContrasenia(
                passwordEncoder.encode(usuDTO.getContrasenia())
        );

        Usuario guardado = usuarioRepo.save(usu);

        return ResponseDTO(guardado);
    }

    private UsuarioResponseDTO ResponseDTO(Usuario usuario) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(usuario.getId_usuario());
        responseDTO.setUsuario(usuario.getUsuario());
        responseDTO.setRol(usuario.getRol());
        responseDTO.setEstado(usuario.getEstado());
        return responseDTO;
    }

    @Override
        public Usuario findUser (String usuario){
            //1. Buscar usuario y validar si existe
            Usuario usu = usuarioRepo.findByUsuario(usuario)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            return usu;
        }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public void bajaLogicaUsuario(Long id_usuario) {
        Usuario usu = usuarioRepo.findById(id_usuario)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));
        usu.setEstado("INACTIVO");
        usuarioRepo.save(usu);
    }

    @Override
    public void altaLogicaUsuario(Long id_usuario) {
        Usuario usu = usuarioRepo.findById(id_usuario)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));
        usu.setEstado("ACTIVO");
        usuarioRepo.save(usu);
    }

    @Override
    public Usuario findUsuario(Long id_usuario) {
        Usuario usu = usuarioRepo.findById(id_usuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no fue encontrado."));
        return usu;
    }

    @Override
    public void editUsuario(Long idUsuario, UsuarioUpdateDTO dto) {
        Usuario usu = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no fue encontrado."));
        usu.setUsuario(dto.getUsuario());
        usu.setRol(dto.getRol());

        usu.setContrasenia(
                passwordEncoder.encode(dto.getContrasenia())
        );

        usuarioRepo.save(usu);
    }
}
