package com.example.Clinica_Odontologica.config;

import com.example.Clinica_Odontologica.jwt.JwtAuthFilter;
import com.example.Clinica_Odontologica.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Component
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) //Habilita @PreAuthorize
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService uds;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    //Password Encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Permite usar AuthenticationManager en AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    //Definimos la fuente de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        //IMPORTANTE: especificar origenes exactos (no "*") si allowedCredential = true
        config.setAllowedOrigins(List.of("http://localhost:5500","http://127.0.0.1:5500","http://localhost"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*")); // headers permitidos (incluye Authorization)
        config.setAllowCredentials(false); // poner true si usás cookies HttpOnly
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            //  Aplicar CORS con la config definida
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //Desactivar CRSF (API stateless con JWT)
                .csrf(csrf -> csrf.disable())

                //Configuracion de rutas publicas y protegidas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error","/", "/index.html", "/login.html", "/css/**", "/js/**",
                                "/vendor/**", "/img/**", "/api/auth/**").permitAll()
                // Ejemplos de rutas protegidas por rol (Usar @PreAuthorize o matchers)
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/odontologo/**").hasRole("ODONTOLOGO")
                                .requestMatchers("/api/secretario/**").hasRole("SECRETARIO")
                                .anyRequest().authenticated()
                )
                // Stateless: no session
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // Registrar nuestro filtro JWT antes del filtro de Spring
                http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
    }
}
