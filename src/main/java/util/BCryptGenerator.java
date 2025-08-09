package com.KimJesus.forohub.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
    public static void main(String[] args) {
        String rawPassword = "123456"; // aquí pones la contraseña en texto plano
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(rawPassword);

        System.out.println("Contraseña original: " + rawPassword);
        System.out.println("Contraseña encriptada: " + hashedPassword);
    }
}

