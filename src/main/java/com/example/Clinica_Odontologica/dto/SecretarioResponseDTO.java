package com.example.Clinica_Odontologica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SecretarioResponseDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String sector;
    private String estado;
}
