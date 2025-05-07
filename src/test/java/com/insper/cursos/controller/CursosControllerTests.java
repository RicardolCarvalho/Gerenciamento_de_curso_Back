package com.insper.cursos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insper.cursos.dto.CursoRequest;
import com.insper.cursos.model.Curso;
import com.insper.cursos.service.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class CursosControllerTests {

    @InjectMocks
    private CursoController controller;

    @Mock
    private CursoService service;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        var validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setValidator(validator)
                .build();

        objectMapper = new ObjectMapper();
    }

    private CursoRequest makeValidRequest() {
        var req = new CursoRequest();
        req.setTitulo("Java Básico");
        req.setDescricao("Aprenda Java");
        req.setCargaHoraria(20);
        req.setInstrutor("João");
        req.setEmailCriador("joao@ex.com");
        return req;
    }

    private Curso makeEntity() {
        var c = new Curso();
        c.setId("id123");
        c.setTitulo("Java Básico");
        c.setDescricao("Aprenda Java");
        c.setCargaHoraria(20);
        c.setInstrutor("João");
        c.setEmailCriador("joao@ex.com");
        return c;
    }

    @Test
    @DisplayName("POST /api/cursos → 201 CREATED")
    void criarCurso_Sucesso() throws Exception {
        var req = makeValidRequest();
        var ent = makeEntity();

        when(service.criar(ArgumentMatchers.any(Curso.class))).thenReturn(ent);

        mockMvc.perform(post("/api/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("id123"))
                .andExpect(jsonPath("$.titulo").value("Java Básico"))
                .andExpect(jsonPath("$.descricao").value("Aprenda Java"));

        verify(service, times(1)).criar(ArgumentMatchers.any(Curso.class));
    }

    @Test
    @DisplayName("POST /api/cursos → 400 BAD REQUEST (validação)")
    void criarCurso_ValidacaoFalha() throws Exception {
        var bad = new CursoRequest();


        mockMvc.perform(post("/api/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bad)))
                .andExpect(status().isBadRequest());

        verify(service, never()).criar(any());
    }

    @Test
    @DisplayName("PUT /api/cursos/{id} → 200 OK")
    void atualizarCurso_Sucesso() throws Exception {
        var req = makeValidRequest();
        var ent = makeEntity();

        when(service.atualizar(eq("id123"), any(Curso.class))).thenReturn(ent);

        mockMvc.perform(put("/api/cursos/{id}", "id123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id123"))
                .andExpect(jsonPath("$.instrutor").value("João"));

        verify(service, times(1)).atualizar(eq("id123"), any(Curso.class));
    }

    @Test
    @DisplayName("PUT /api/cursos/{id} → 400 BAD REQUEST (validação)")
    void atualizarCurso_ValidacaoFalha() throws Exception {
        var bad = new CursoRequest();

        mockMvc.perform(put("/api/cursos/{id}", "id123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bad)))
                .andExpect(status().isBadRequest());

        verify(service, never()).atualizar(anyString(), any());
    }

    @Test
    @DisplayName("GET /api/cursos → 200 OK")
    void listarCursos_Sucesso() throws Exception {
        var c1 = makeEntity();
        var c2 = makeEntity();
        c2.setId("id456");
        when(service.listar()).thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/api/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].id").value("id456"));

        verify(service, times(1)).listar();
    }

    @Test
    @DisplayName("GET /api/cursos/{id} → 200 OK")
    void detalhesCurso_Sucesso() throws Exception {
        var ent = makeEntity();
        when(service.buscarPorId("id123")).thenReturn(ent);

        mockMvc.perform(get("/api/cursos/{id}", "id123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id123"))
                .andExpect(jsonPath("$.emailCriador").value("joao@ex.com"));

        verify(service, times(1)).buscarPorId("id123");
    }

    @Test
    @DisplayName("DELETE /api/cursos/{id} → 204 NO CONTENT")
    void excluirCurso_Sucesso() throws Exception {
        doNothing().when(service).excluir("id123", "tokenXYZ");

        mockMvc.perform(delete("/api/cursos/{id}", "id123")
                        .header("Authorization", "Bearer tokenXYZ"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).excluir("id123", "tokenXYZ");
    }

    @Test
   @DisplayName("DELETE /api/cursos/{id} → 400 BAD REQUEST (header mal formado)")
   void excluirCurso_HeaderMalFormado() throws Exception {
               mockMvc.perform(delete("/api/cursos/{id}", "id123"))
                           .andExpect(status().isBadRequest());
           }
}
