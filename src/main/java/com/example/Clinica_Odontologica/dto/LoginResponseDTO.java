package com.example.Clinica_Odontologica.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LoginResponseDTO {
    String token;
    String username;
    List<String> roles;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}
