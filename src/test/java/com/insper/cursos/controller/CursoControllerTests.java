package com.insper.cursos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insper.cursos.dto.CursoRequest;
import com.insper.cursos.dto.CursoResponse;
import com.insper.cursos.model.Curso;
import com.insper.cursos.service.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CursoControllerTests {

    @InjectMocks
    private CursoController cursoController;

    @Mock
    private CursoService cursoService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cursoController).build();
        this.objectMapper = new ObjectMapper();
    }

    private Curso createCurso() {
        Curso curso = new Curso();
        curso.setId("123");
        curso.setTitulo("Java Básico");
        curso.setDescricao("Aprenda Java do zero");
        curso.setCargaHoraria(20);
        curso.setInstrutor("João Silva");
        curso.setEmailCriador("joao@email.com");
        return curso;
    }

    private CursoRequest createCursoRequest() {
        CursoRequest req = new CursoRequest();
        req.setTitulo("Java Básico");
        req.setDescricao("Aprenda Java do zero");
        req.setCargaHoraria(20);
        req.setInstrutor("João Silva");
        req.setEmailCriador("joao@email.com");
        return req;
    }

    @Test
    void testCriarCurso() throws Exception {
        Curso curso = createCurso();
        CursoRequest request = createCursoRequest();

        Mockito.when(cursoService.criar(any(Curso.class))).thenReturn(curso);

        mockMvc.perform(post("/api/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Java Básico"))
                .andExpect(jsonPath("$.descricao").value("Aprenda Java do zero"));
    }

    @Test
    void testAtualizarCurso() throws Exception {
        Curso curso = createCurso();
        CursoRequest request = createCursoRequest();

        Mockito.when(cursoService.atualizar(eq("123"), any(Curso.class))).thenReturn(curso);

        mockMvc.perform(put("/api/cursos/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrutor").value("João Silva"));
    }

    @Test
    void testListarCursos() throws Exception {
        List<Curso> cursos = Arrays.asList(createCurso(), createCurso());
        Mockito.when(cursoService.listar()).thenReturn(cursos);

        mockMvc.perform(get("/api/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testDetalhesCurso() throws Exception {
        Curso curso = createCurso();
        Mockito.when(cursoService.buscarPorId("123")).thenReturn(curso);

        mockMvc.perform(get("/api/cursos/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void testExcluirCurso() throws Exception {
        mockMvc.perform(delete("/api/cursos/123"))
                .andExpect(status().isNoContent());
    }
}
