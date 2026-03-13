package com.example.Clinica_Odontologica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SecretarioRequestDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private LocalDate fecha_nac;
    private String sector;
    private Long id_usuario;
}
