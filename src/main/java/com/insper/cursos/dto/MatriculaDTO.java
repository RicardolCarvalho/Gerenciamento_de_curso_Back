package com.insper.cursos.dto;

public class MatriculaDTO {
    private String id;
    private String alunoId;
    private String cursoId;
    private String dataMatricula;

    public MatriculaDTO() {
    }
    public MatriculaDTO(String id, String alunoId, String cursoId, String dataMatricula) {
        this.id = id;
        this.alunoId = alunoId;
        this.cursoId = cursoId;
        this.dataMatricula = dataMatricula;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAlunoId() {
        return alunoId;
    }
    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }
    public String getCursoId() {
        return cursoId;
    }
    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }
    public String getDataMatricula() {
        return dataMatricula;
    }
    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
}
