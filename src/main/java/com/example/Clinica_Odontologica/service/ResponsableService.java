package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.ResponsableRequestDTO;
import com.example.Clinica_Odontologica.dto.ResponsableResponseDTO;
import com.example.Clinica_Odontologica.model.Responsable;
import com.example.Clinica_Odontologica.repository.IResponsableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResponsableService implements IResponsableService {

    @Autowired
    private IResponsableRepository responsableRepo;

    @Override
    public ResponsableResponseDTO createResponsable(ResponsableRequestDTO respDto) {
        Responsable resp = new Responsable();

        resp.setDni(respDto.getDni());
        resp.setNombre(respDto.getNombre());
        resp.setApellido(respDto.getApellido());
        resp.setTelefono(respDto.getTelefono());
        resp.setDireccion(respDto.getDireccion());
        resp.setFecha_nac(respDto.getFecha_nac());
        resp.setEstado("ACTIVO");
        resp.setTipo_relacion(respDto.getTipo_relacion());

        Responsable responsableResp = responsableRepo.save(resp);
        return respResponse(responsableResp);
    }

    private ResponsableResponseDTO respResponse(Responsable responsableResp) {
        ResponsableResponseDTO respDto = new ResponsableResponseDTO();
        respDto.setDni(responsableResp.getDni());
        respDto.setNombre(responsableResp.getNombre());
        respDto.setApellido(responsableResp.getApellido());
        respDto.setFecha_nac(responsableResp.getFecha_nac());
        respDto.setEstado(responsableResp.getEstado());
        respDto.setTipo_relacion(responsableResp.getTipo_relacion());

        return respDto;
    }

    @Override
    public List<ResponsableResponseDTO> getResponsablesActivos() {

        List<Responsable> responsables = responsableRepo.findByEstado("ACTIVO");
        List<ResponsableResponseDTO> dtoList = new ArrayList<>();

        for (Responsable r : responsables) {
            ResponsableResponseDTO dto = new ResponsableResponseDTO(
                    r.getDni(),
                    r.getNombre(),
                    r.getApellido(),
                    r.getFecha_nac(),
                    r.getEstado(),
                    r.getTipo_relacion()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public ResponsableResponseDTO editResponsable(Long id_resp, ResponsableRequestDTO respDto) {
        Responsable resp = responsableRepo.findById(id_resp)
                .orElseThrow(() -> new RuntimeException("El responsable no ha sido encontrado."));
        resp.setDni(respDto.getDni());
        resp.setNombre(respDto.getNombre());
        resp.setApellido(respDto.getApellido());
        resp.setTelefono(respDto.getTelefono());
        resp.setDireccion(respDto.getDireccion());
        resp.setFecha_nac(respDto.getFecha_nac());
        resp.setTipo_relacion(respDto.getTipo_relacion());

        Responsable nuevoResp = responsableRepo.save(resp);

        return respResponse(nuevoResp);
    }

    @Override
    public ResponsableResponseDTO bajaLogicaResponsable(Long id_resp) {
        Responsable resp = responsableRepo.findByIdAndEstado(id_resp,"ACTIVO")
                .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado"));
        resp.setEstado("INACTIVO");
        Responsable nuevoResp = responsableRepo.save(resp);
        return respResponse(nuevoResp);
    }

    @Override
    public ResponsableResponseDTO altaLogicaResponsable(Long id_resp) {
        Responsable resp = responsableRepo.findById(id_resp)
                .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado"));
        resp.setEstado("ACTIVO");
        Responsable nuevoResp = responsableRepo.save(resp);
        return respResponse(nuevoResp);
    }

    @Override
    public void deleteResponsable(Long id_resp) {
        Responsable resp = responsableRepo.findById(id_resp)
                .orElseThrow(() -> new RuntimeException("El Responsable no ha sido encontrado."));

        if(!resp.getListaPacientes().isEmpty()){
            throw new RuntimeException("Este responsable tiene Pacientes asignados.");
        }
        responsableRepo.delete(resp);
    }

    @Override
    public List<ResponsableResponseDTO> getAllResponsablesAdmin() {
        List<Responsable> responsables = responsableRepo.findAll();
        List<ResponsableResponseDTO> dtoList = new ArrayList<>();

        for (Responsable r : responsables) {
            ResponsableResponseDTO dto = new ResponsableResponseDTO(
                    r.getDni(),
                    r.getNombre(),
                    r.getApellido(),
                    r.getFecha_nac(),
                    r.getEstado(),
                    r.getTipo_relacion()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
}
