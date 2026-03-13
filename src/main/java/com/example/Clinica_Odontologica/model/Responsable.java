package com.example.Clinica_Odontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Responsable extends Persona{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsable")
    private Long id;
    private String tipo_relacion; //PADRE, MADRE, TUTOR, ETC.
    private String estado; // ACTIVO / INACTIVO
    @OneToMany(mappedBy = "unResponsable",
    cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Paciente>listaPacientes;

}
