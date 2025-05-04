package com.insper.cursos.dto;

import com.insper.cursos.model.Avaliacao;

import java.util.UUID;

public class AvaliacaoResponse {
    private UUID id;
    private String emailAluno;
    private Integer nota;
    private String titulo;
    private String descricao;
    private UUID cursoId;

    public AvaliacaoResponse(UUID id, String emailAluno, Integer nota, String titulo,
                              String descricao, UUID cursoId) {
        this.id = id;
        this.emailAluno = emailAluno;
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cursoId = cursoId;
    }

    public static AvaliacaoResponse fromEntity(Avaliacao a) {
        return new AvaliacaoResponse(
            a.getId(),
            a.getEmailAluno(),
            a.getNota(),
            a.getTitulo(),
            a.getDescricao(),
            a.getCursoId()
        );
    }

    public UUID getId() { return id; }
    public String getEmailAluno() { return emailAluno; }
    public Integer getNota() { return nota; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public UUID getCursoId() { return cursoId; }
}
