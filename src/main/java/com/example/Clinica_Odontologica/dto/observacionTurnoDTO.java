package com.example.Clinica_Odontologica.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class observacionTurnoDTO {
    private LocalDate fechaTurno;
    private LocalTime horaTurno;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String observacion;

    public observacionTurnoDTO() {
    }

    public observacionTurnoDTO(LocalDate fechaTurno, LocalTime horaTurno, String nombrePaciente, String apellidoPaciente, String observacion) {
        this.fechaTurno = fechaTurno;
        this.horaTurno = horaTurno;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.observacion = observacion;
    }
}
