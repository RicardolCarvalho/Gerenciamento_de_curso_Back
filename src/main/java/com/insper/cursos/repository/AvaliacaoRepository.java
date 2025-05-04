package com.insper.cursos.repository;

import com.insper.cursos.model.Avaliacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface AvaliacaoRepository extends MongoRepository<Avaliacao, UUID> {
    List<Avaliacao> findByCursoId(UUID cursoId);
}
