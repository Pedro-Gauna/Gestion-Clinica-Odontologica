package com.example.Clinica_Odontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Long id;
    private LocalDate fechaTurno;
    private LocalTime horaTurno;
    private String estado;
    private String observacion;
    private String motivoCancelacion;
    //FK_ODONTOLOGO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_odontologo")
    private Odontologo unOdontologo;
    //FK_PACIENTE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_paciente")
    private Paciente unPaciente;


}
