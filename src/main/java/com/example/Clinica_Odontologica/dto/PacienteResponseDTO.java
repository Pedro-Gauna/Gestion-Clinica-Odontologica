package com.example.Clinica_Odontologica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PacienteResponseDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fecha_nac;
    private String estado;
    private boolean tiene_OS;
    private String tipo_tratamiento;
    private Long id_responsable;
}
