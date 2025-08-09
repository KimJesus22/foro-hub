package com.KimJesus.forohub.config;

import com.KimJesus.forohub.model.Usuario;
import com.KimJesus.forohub.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String login = "miusuario";     // usuario para login
        String rawPassword = "123456";  // contraseña que usarás en Postman

        if (usuarioRepository.findByLogin(login).isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setPassword(passwordEncoder.encode(rawPassword)); // encripta con BCrypt
            usuario.setRole("ROLE_USER");

            usuarioRepository.save(usuario);
            System.out.println("✅ Usuario creado: " + login + " / contraseña: " + rawPassword);
        } else {
            System.out.println("ℹ Usuario ya existe: " + login);
        }
    }
}
