package com.insper.cursos.dto;

import com.insper.cursos.model.Avaliacao;

public class AvaliacaoResponse {

    private String id;
    private String emailAluno;
    private Integer nota;
    private String titulo;
    private String descricao;
    private String cursoId;

    public AvaliacaoResponse(String id, String emailAluno, Integer nota,
                              String titulo, String descricao, String cursoId) {
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

    public String getId() { return id; }
    public String getEmailAluno() { return emailAluno; }
    public Integer getNota() { return nota; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getCursoId() { return cursoId; }
}