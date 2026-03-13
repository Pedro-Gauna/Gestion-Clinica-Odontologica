package com.example.Clinica_Odontologica.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @Column(unique = true, nullable = false)
    private String usuario;
    @Column(nullable = false)
    private String contrasenia;
    @Column(nullable = false)
    private String rol;
    private String estado; // ACTIVO / INACTIVO
    @OneToOne(mappedBy = "usuario")
    private Odontologo unOdontologo;
    @OneToOne(mappedBy = "usuario")
    private Secretario unSecretario;
}
