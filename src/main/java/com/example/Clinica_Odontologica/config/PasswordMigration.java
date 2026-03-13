package com.example.Clinica_Odontologica.config;

import com.example.Clinica_Odontologica.model.Usuario;
import com.example.Clinica_Odontologica.repository.IUsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordMigration {

    @Bean
    CommandLineRunner encodePasswordIfNeed(IUsuarioRepository repo, PasswordEncoder encoder){
        return args -> {
          for(Usuario usu: repo.findAll()){
            String pass = usu.getContrasenia();
            if(pass == null) continue;
                if(!(pass.startsWith("$2a$") || pass.startsWith("$2b$") || pass.startsWith("$2y$"))){
                    //Suponemos que esta en texto plano: codificamos y guardamos
                    usu.setContrasenia(encoder.encode(pass));
                    repo.save(usu);
                    System.out.println("Codificada contraseña para: " + usu.getUsuario());
                }

          }
        };

    }
}
