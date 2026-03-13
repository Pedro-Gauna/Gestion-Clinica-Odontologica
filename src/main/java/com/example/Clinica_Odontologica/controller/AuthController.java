package com.example.Clinica_Odontologica.controller;

import com.example.Clinica_Odontologica.dto.LoginRequestDTO;
import com.example.Clinica_Odontologica.dto.LoginResponseDTO;
import com.example.Clinica_Odontologica.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO){
       try{
         Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getUsuario(),requestDTO.getContrasenia())
         );

           UserDetails user = (UserDetails)auth.getPrincipal();
           String token = jwtUtil.generateToken(user);

           List<String> roles = user.getAuthorities().stream()
                   .map(GrantedAuthority::getAuthority)
                   .map(r -> r.startsWith("ROLE_") ? r.substring(5) : r) // quitar prefijo para front
                   .collect(Collectors.toList());

           return ResponseEntity.ok(new LoginResponseDTO(token, user.getUsername(), roles));
       }catch (BadCredentialsException ex) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                   .body(Map.of("error","Credenciales invalidas"));
       }

   }





}
