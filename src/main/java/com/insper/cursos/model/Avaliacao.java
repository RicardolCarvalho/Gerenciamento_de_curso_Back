package com.insper.cursos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Document(collection = "avaliacoes")
public class Avaliacao {
    @Id
    private UUID id;

    @NotBlank
    private String emailAluno;

    @Min(1)
    @Max(5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private UUID cursoId;

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
}
