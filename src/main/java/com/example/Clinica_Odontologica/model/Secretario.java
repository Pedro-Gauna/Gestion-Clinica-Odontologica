package com.example.Clinica_Odontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Secretario extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_secretario")
    private Long id;
    private String sector;
    private String estado; // ACTIVO / INACTIVO
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

}