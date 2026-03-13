package com.example.Clinica_Odontologica.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@MappedSuperclass
public abstract class Persona {
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private LocalDate fecha_nac;

    public Persona() {
    }

    public Persona(String dni, String nombre, String apellido, String telefono, String direccion, LocalDate fecha_nac) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fecha_nac = fecha_nac;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_nac=" + fecha_nac +
                '}';
    }
}
