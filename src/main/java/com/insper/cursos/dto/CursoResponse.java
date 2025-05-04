package com.insper.cursos.dto;

import com.insper.cursos.model.Curso;

import java.util.UUID;

public class CursoResponse {
    private UUID id;
    private String titulo;
    private String descricao;
    private Integer cargaHoraria;
    private String instrutor;
    private String emailCriador;

    public CursoResponse(UUID id, String titulo, String descricao,
                         Integer cargaHoraria, String instrutor, String emailCriador) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.instrutor = instrutor;
        this.emailCriador = emailCriador;
    }

    public static CursoResponse fromEntity(Curso c) {
        return new CursoResponse(
            c.getId(),
            c.getTitulo(),
            c.getDescricao(),
            c.getCargaHoraria(),
            c.getInstrutor(),
            c.getEmailCriador()
        );
    }

    public UUID getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public Integer getCargaHoraria() { return cargaHoraria; }
    public String getInstrutor() { return instrutor; }
    public String getEmailCriador() { return emailCriador; }
}
