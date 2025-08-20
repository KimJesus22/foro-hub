package com.KimJesus.forohub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "topico")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private final LocalDateTime fechaCreacion = LocalDateTime.now();

    private String status = "ABIERTO";

    @NotBlank(message = "El autor no puede estar vacío")
    private String autor;

    @NotBlank(message = "El curso no puede estar vacío")
    private String curso;

    // Getters y setters (igual que antes)

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}

