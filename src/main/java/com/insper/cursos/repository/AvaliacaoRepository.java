package com.insper.cursos.repository;

import com.insper.cursos.model.Avaliacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AvaliacaoRepository extends MongoRepository<Avaliacao, String> {
    List<Avaliacao> findByCursoId(String cursoId);
}