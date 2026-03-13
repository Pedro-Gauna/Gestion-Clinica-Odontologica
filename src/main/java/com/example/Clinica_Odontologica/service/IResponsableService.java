package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.ResponsableRequestDTO;
import com.example.Clinica_Odontologica.dto.ResponsableResponseDTO;

import java.util.List;

public interface IResponsableService {

    public ResponsableResponseDTO createResponsable(ResponsableRequestDTO respDto);

    public List<ResponsableResponseDTO>getResponsablesActivos();

    public ResponsableResponseDTO editResponsable(Long id_resp,ResponsableRequestDTO respDto);

    public ResponsableResponseDTO bajaLogicaResponsable(Long id_resp);

    public ResponsableResponseDTO altaLogicaResponsable(Long id_resp);

    public void deleteResponsable(Long id_resp);

    List<ResponsableResponseDTO> getAllResponsablesAdmin();
}
