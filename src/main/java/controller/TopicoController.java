package com.KimJesus.forohub.controller;

import com.KimJesus.forohub.model.Topico;
import com.KimJesus.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Topico topico) {
        try {
            Topico creado = service.crear(topico);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            // Devuelve código 409 Conflict con el mensaje de error
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Topico>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Topico> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizar(@PathVariable Long id, @Valid @RequestBody Topico topico) {
        try {
            return ResponseEntity.ok(service.actualizar(id, topico));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();// 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found si no existe
        }
    }
}







