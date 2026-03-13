package com.example.Clinica_Odontologica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Entity
public class Paciente extends Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tiene_os")
    private boolean tieneOS;
    private String tipo_tratamiento;
    private String estado;
    //Paciente
    @OneToMany(mappedBy = "unPaciente",
    cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    orphanRemoval = true,
    fetch = FetchType.LAZY)
    private List<Turno> listaTurnos;
    //Responsable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_responsable")
    private Responsable unResponsable;

    public Paciente() {
    }

    public Paciente(String dni, String nombre, String apellido, String telefono, String direccion, LocalDate fecha_nac, Long id_paciente, boolean tieneOS, String tipo_tratamiento, String estado, Responsable unResponsable) {
        super(dni, nombre, apellido, telefono, direccion, fecha_nac);
        this.id = id_paciente;
        this.tieneOS = tieneOS;
        this.tipo_tratamiento = tipo_tratamiento;
        this.estado = estado;
        this.unResponsable = unResponsable;
    }
}
