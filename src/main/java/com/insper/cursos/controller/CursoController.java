package com.insper.cursos.controller;

import com.insper.cursos.dto.CursoRequest;
import com.insper.cursos.dto.CursoResponse;
import com.insper.cursos.model.Curso;
import com.insper.cursos.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoResponse criar(@Valid @RequestBody CursoRequest req) {
        Curso saved = service.criar(req.toEntity());
        return CursoResponse.fromEntity(saved);
    }

    @PutMapping("/{id}")
    public CursoResponse atualizar(@PathVariable String id,
                                   @Valid @RequestBody CursoRequest req) {
        Curso updated = service.atualizar(id, req.toEntity());
        return CursoResponse.fromEntity(updated);
    }

    @GetMapping
    public List<CursoResponse> listar() {
        return service.listar().stream()
                .map(CursoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CursoResponse detalhes(@PathVariable String id) {
        return CursoResponse.fromEntity(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable String id,
            @RequestHeader("Authorization") String authHeader
    ) {
        // header vem como "Bearer xyz"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Header Authorization mal formado");
        }
        String token = authHeader.substring(7);
        service.excluir(id, token);
    }
}
