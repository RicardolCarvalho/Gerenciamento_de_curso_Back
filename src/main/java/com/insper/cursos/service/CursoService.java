package com.insper.cursos.service;

import com.insper.cursos.config.Grupo2Config;
import com.insper.cursos.exception.BusinessException;
import com.insper.cursos.exception.ResourceNotFoundException;
import com.insper.cursos.model.Curso;
import com.insper.cursos.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    private final CursoRepository repo;
    private final Grupo2Config.Grupo2Client grupo2Client;

    public CursoService(CursoRepository repo, Grupo2Config.Grupo2Client grupo2Client) {
        this.repo = repo;
        this.grupo2Client = grupo2Client;
    }

    public Curso criar(Curso curso) {
        return repo.save(curso);
    }

    public Curso atualizar(String id, Curso curso) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Curso não encontrado: " + id);
        }
        curso.setId(id);
        return repo.save(curso);
    }

    public List<Curso> listar() {
        return repo.findAll();
    }

    public Curso buscarPorId(String id) {
        return repo.findById(id)
                   .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado: " + id));
    }

    public void excluir(String id) {
        boolean has = grupo2Client.hasMatriculas(id).block();
        if (has) {
            throw new BusinessException("Não é possível excluir; existem matrículas.");
        }
        repo.deleteById(id);
    }
}