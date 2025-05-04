package com.insper.cursos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import com.insper.cursos.model.Curso;

public class CursoRequest {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @Positive
    private Integer cargaHoraria;

    @NotBlank
    private String instrutor;

    @NotBlank
    private String emailCriador;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    public String getInstrutor() { return instrutor; }
    public void setInstrutor(String instrutor) { this.instrutor = instrutor; }
    public String getEmailCriador() { return emailCriador; }
    public void setEmailCriador(String emailCriador) { this.emailCriador = emailCriador; }

    public Curso toEntity() {
        Curso c = new Curso();
        c.setTitulo(this.titulo);
        c.setDescricao(this.descricao);
        c.setCargaHoraria(this.cargaHoraria);
        c.setInstrutor(this.instrutor);
        c.setEmailCriador(this.emailCriador);
        return c;
    }
}