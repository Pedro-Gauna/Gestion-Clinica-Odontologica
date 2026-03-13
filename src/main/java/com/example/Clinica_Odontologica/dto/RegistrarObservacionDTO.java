package com.example.Clinica_Odontologica.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrarObservacionDTO {
    private String observacion;

    public RegistrarObservacionDTO() {
    }

    public RegistrarObservacionDTO(String observacion) {
        this.observacion = observacion;
    }
}
