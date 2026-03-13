package com.example.Clinica_Odontologica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TurnoResponseDTO {
    //Datos del Paciente
    private String nombrePac;
    private String apellidoPac;
    private String tipo_tratamiento;
    //Datos del turno
    private LocalTime horaTurno;
    private LocalDate fechaTurno;
    private String estadoTurno;
    //Datos Odontologo
    private String nombreOdonto;

}
