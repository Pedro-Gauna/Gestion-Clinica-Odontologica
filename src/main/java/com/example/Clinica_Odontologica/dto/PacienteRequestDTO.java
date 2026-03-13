package com.example.Clinica_Odontologica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PacienteRequestDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private LocalDate fecha_nac;
    private boolean tiene_OS;
    private String tipo_tratamiento;
    private Long id_responsable;
}
