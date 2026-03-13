package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.OdontologoRequestDTO;
import com.example.Clinica_Odontologica.dto.OdontologoResponseDTO;
import com.example.Clinica_Odontologica.dto.OdontologoUpdateDTO;
import com.example.Clinica_Odontologica.model.Odontologo;
import com.example.Clinica_Odontologica.model.Usuario;
import com.example.Clinica_Odontologica.repository.IOdontologoRepository;
import com.example.Clinica_Odontologica.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OdontologoService implements IOdontologoService{

    @Autowired
    private IOdontologoRepository odontoRepo;

    @Autowired
    private IUsuarioRepository usuRepo;

    @Override
    public OdontologoResponseDTO createOdontologo(OdontologoRequestDTO odontoDTO) {

        Usuario usuario = usuRepo.findById(odontoDTO.getId_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(!usuario.getRol().equals("ODONTOLOGO")){
            throw new RuntimeException("El usuario no tiene rol Odontologo");
        }

        if(usuario.getUnOdontologo() != null){
            throw new RuntimeException("El usuario ya tiene un Odontólogo asociado");
        }

        Odontologo odonto = new Odontologo();
        odonto.setDni(odontoDTO.getDni());
        odonto.setNombre(odontoDTO.getNombre());
        odonto.setApellido(odontoDTO.getApellido());
        odonto.setTelefono(odontoDTO.getTelefono());
        odonto.setDireccion(odontoDTO.getDireccion());
        odonto.setFecha_nac(odontoDTO.getFecha_nac());
        odonto.setEspecialidad(odontoDTO.getEspecialidad());
        odonto.setEstado("ACTIVO");
        odonto.setUsuario(usuario);

        Odontologo odontologo = odontoRepo.save(odonto);

        return ResponseOdonto(odontologo);
    }

    private OdontologoResponseDTO ResponseOdonto(Odontologo odontologo) {
        OdontologoResponseDTO odontoResponse = new OdontologoResponseDTO();
        odontoResponse.setDni(odontologo.getDni());
        odontoResponse.setNombre(odontologo.getNombre());
        odontoResponse.setApellido(odontologo.getApellido());
        odontoResponse.setEspecialidad(odontologo.getEspecialidad());
        odontoResponse.setEstado(odontologo.getEstado());
        return odontoResponse;
    }

    @Override
    public List<OdontologoResponseDTO> getOdontologosActivos(){
        List<Odontologo> activos = odontoRepo.findByEstado("ACTIVO");
        List<OdontologoResponseDTO> odontoDto = new ArrayList<>();

        for(Odontologo o: activos){

            odontoDto.add(mapToDTO(o));
        }
        return odontoDto;
    }

    private OdontologoResponseDTO mapToDTO(Odontologo o) {
        return new OdontologoResponseDTO(
                o.getDni(),
                o.getNombre(),
                o.getApellido(),
                o.getEspecialidad(),
                o.getEstado()
        );
    }

    @Override
    public List<Odontologo> getOdontologosAdmin() {
        return odontoRepo.findAll();
    }

    @Override
    public OdontologoResponseDTO getOdontologoActivo(Long id_odonto) {
        Odontologo odonto = odontoRepo.findByIdAndEstado(id_odonto,"ACTIVO")
                .orElseThrow(() -> new IllegalArgumentException("El odontologo no existe."));
        OdontologoResponseDTO nuevoOdonto = ResponseOdonto(odonto);
        return nuevoOdonto;
    }

    @Override
    public OdontologoUpdateDTO editOdontologo(Long id_odonto, OdontologoRequestDTO odontoDto){
         Odontologo odonto = odontoRepo.findById(id_odonto)
                 .orElseThrow(()-> new RuntimeException("El Odontologo no fue encontrado."));
         odonto.setDni(odontoDto.getDni());
        odonto.setNombre(odontoDto.getNombre());
        odonto.setApellido(odontoDto.getApellido());
        odonto.setTelefono(odontoDto.getTelefono());
        odonto.setDireccion(odontoDto.getDireccion());
        odonto.setFecha_nac(odontoDto.getFecha_nac());
        odonto.setEspecialidad(odontoDto.getEspecialidad());

        Odontologo nuevo = odontoRepo.save(odonto);
        return responseUpdate(nuevo);
    }

    private OdontologoUpdateDTO responseUpdate(Odontologo odonto) {
        OdontologoUpdateDTO dto = new OdontologoUpdateDTO();
        dto.setDni(odonto.getDni());
        dto.setNombre(odonto.getNombre());
        dto.setApellido(odonto.getApellido());
        dto.setTelefono(odonto.getTelefono());
        dto.setDireccion(odonto.getDireccion());
        dto.setFecha_nac(odonto.getFecha_nac());
        dto.setEspecialidad(odonto.getEspecialidad());
        return dto;
    }

    @Override
    public OdontologoResponseDTO bajaLogicaOdontologo(Long id_odonto) {
        Odontologo odonto = odontoRepo.findByIdAndEstado(id_odonto, "ACTIVO")
                .orElseThrow(() -> new IllegalArgumentException("Odontologo no encontrado."));
        odonto.setEstado("INACTIVO");
        Odontologo odontologo = odontoRepo.save(odonto);
        return ResponseOdonto(odontologo);
    }

    @Override
    public OdontologoResponseDTO altaLogicaOdontologo(Long id_odonto) {
        Odontologo odonto = odontoRepo.findById(id_odonto)
                .orElseThrow(() -> new IllegalArgumentException("Odontologo no encontrado"));
        odonto.setEstado("ACTIVO");
        Odontologo odontologo = odontoRepo.save(odonto);
        return ResponseOdonto(odontologo);
    }

}
