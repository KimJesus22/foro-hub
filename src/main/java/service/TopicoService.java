package com.KimJesus.forohub.service;

import com.KimJesus.forohub.model.Topico;
import com.KimJesus.forohub.repository.TopicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    private final TopicoRepository repo;

    public TopicoService(TopicoRepository repo) {
        this.repo = repo;
    }

    public Topico crear(Topico topico) {
        List<Topico> existentes = repo.findByTituloAndMensaje(topico.getTitulo(), topico.getMensaje());
        if (!existentes.isEmpty()) {
            throw new IllegalArgumentException("Ya existe un tópico con ese título y mensaje");
        }
        return repo.save(topico);
    }

    public List<Topico> listarTodos() {
        return repo.findAll();
    }


    public Optional<Topico> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    public Topico actualizar(Long id, Topico datos) {
        Optional<Topico> existente = repo.findById(id);
        if (existente.isEmpty()) {
            throw new RuntimeException("Tópico no encontrado");
        }

        List<Topico> duplicados = repo.findByTituloAndMensaje(datos.getTitulo(), datos.getMensaje());
        for (Topico t : duplicados) {
            if (!t.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe un tópico con ese título y mensaje");
            }
        }

        Topico t = existente.get();
        t.setTitulo(datos.getTitulo());
        t.setMensaje(datos.getMensaje());
        t.setStatus(datos.getStatus());
        t.setAutor(datos.getAutor());
        t.setCurso(datos.getCurso());

        return repo.save(t);
    }

    public void eliminar(Long id) {
        Optional<Topico> topicoExistente = repo.findById(id);
        if (topicoExistente.isPresent()) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Tópico no encontrado");
        }
    }
}