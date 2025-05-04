package com.insper.cursos.dto;

import com.insper.cursos.model.Avaliacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class AvaliacaoRequest {
    @NotBlank
    private String emailAluno;

    @Min(1) @Max(5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private UUID cursoId;

    public String getEmailAluno() { return emailAluno; }
    public void setEmailAluno(String emailAluno) { this.emailAluno = emailAluno; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public UUID getCursoId() { return cursoId; }
    public void setCursoId(UUID cursoId) { this.cursoId = cursoId; }

    public Avaliacao toEntity() {
        Avaliacao a = new Avaliacao();
        a.setEmailAluno(this.emailAluno);
        a.setNota(this.nota);
        a.setTitulo(this.titulo);
        a.setDescricao(this.descricao);
        a.setCursoId(this.cursoId);
        return a;
    }
}
