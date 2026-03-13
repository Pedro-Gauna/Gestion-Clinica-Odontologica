package com.example.Clinica_Odontologica.jwt;

import com.example.Clinica_Odontologica.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        System.out.println("Autorizacion Header: " + authHeader);

        // 1) Extraer Token Bearer
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwt = authHeader.substring(7);
            try{
                username = jwtUtil.extractUsername(jwt);
            }catch (Exception ex){
                //username queda null
                logger.debug("No se pudo extraer Username del JWT: " + ex.getMessage());
            }
        }

        System.out.println("Extradted Username from Token: " + username);

        // 2) Si obtuvimos username y no estamos autenticados en el contexto validar
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt, userDetails)){
                // 3) Construir Authentication y setear en SecurityContext
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("Token valid? : " + jwtUtil.validateToken(jwt,userDetails));

            } else {
                logger.debug("JWT no valido para el usuario: " + username);
            }

            System.out.println("UserDetails y authorities: " + userDetails.getUsername() + userDetails.getAuthorities());
        }


        // 4) Continuar la cadena de filtros

        filterChain.doFilter(request,response);
    }
}
