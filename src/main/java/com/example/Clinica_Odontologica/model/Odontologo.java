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
public class Odontologo extends Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odontologo")
    private Long id;
    private String especialidad;
    private String estado;
    @OneToMany(mappedBy = "unOdontologo",
    cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    orphanRemoval = true,
    fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Turno> listaTurnos;
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

}
