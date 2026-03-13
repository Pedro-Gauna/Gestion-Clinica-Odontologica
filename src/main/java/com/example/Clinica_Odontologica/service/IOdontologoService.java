package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.OdontologoRequestDTO;
import com.example.Clinica_Odontologica.dto.OdontologoResponseDTO;
import com.example.Clinica_Odontologica.dto.OdontologoUpdateDTO;
import com.example.Clinica_Odontologica.model.Odontologo;

import java.util.List;

public interface IOdontologoService {

    public OdontologoResponseDTO createOdontologo(OdontologoRequestDTO odontoDTO);

    public List<OdontologoResponseDTO>getOdontologosActivos();

    public List<Odontologo> getOdontologosAdmin();

    public OdontologoResponseDTO getOdontologoActivo(Long id_odonto);

    public OdontologoUpdateDTO editOdontologo(Long id_odonto, OdontologoRequestDTO odontoDto);

    public OdontologoResponseDTO bajaLogicaOdontologo(Long id_odonto);

    public OdontologoResponseDTO altaLogicaOdontologo(Long id_odonto);

}
