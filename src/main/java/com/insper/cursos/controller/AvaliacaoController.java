package com.insper.cursos.controller;

import com.insper.cursos.dto.AvaliacaoRequest;
import com.insper.cursos.dto.AvaliacaoResponse;
import com.insper.cursos.model.Avaliacao;
import com.insper.cursos.service.AvaliacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoResponse criar(@Valid @RequestBody AvaliacaoRequest req) {
        Avaliacao saved = service.criar(req.toEntity());
        return AvaliacaoResponse.fromEntity(saved);
    }

    @GetMapping("/curso/{cursoId}")
    public List<AvaliacaoResponse> listarPorCurso(@PathVariable String cursoId) {
        return service.listarPorCurso(cursoId).stream()
                      .map(AvaliacaoResponse::fromEntity)
                      .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable String id) {
        service.excluir(id);
    }
}