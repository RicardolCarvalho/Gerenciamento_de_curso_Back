package com.insper.cursos.service;

import com.insper.cursos.exception.ResourceNotFoundException;
import com.insper.cursos.model.Avaliacao;
import com.insper.cursos.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository repo;

    public AvaliacaoService(AvaliacaoRepository repo) {
        this.repo = repo;
    }

    public Avaliacao criar(Avaliacao a) {
        return repo.save(a);
    }

    public List<Avaliacao> listarPorCurso(String cursoId) {
        return repo.findByCursoId(cursoId);
    }

    public void excluir(String id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Avaliação não encontrada: " + id);
        }
        repo.deleteById(id);
    }
}