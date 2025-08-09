package com.KimJesus.forohub.repository;

import com.KimJesus.forohub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByTituloAndMensaje(String titulo, String mensaje);
}

