package com.insper.cursos.repository;

import com.insper.cursos.model.Curso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CursoRepository extends MongoRepository<Curso, UUID> {
}
