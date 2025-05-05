package com.insper.cursos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insper.cursos.dto.AvaliacaoRequest;
import com.insper.cursos.model.Avaliacao;
import com.insper.cursos.service.AvaliacaoService;
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
public class AvaliacaoControllerTests {

    @InjectMocks
    private AvaliacaoController controller;

    @Mock
    private AvaliacaoService service;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.objectMapper = new ObjectMapper();
    }

    private Avaliacao createAvaliacao() {
        Avaliacao a = new Avaliacao();
        a.setId("av1");
        a.setEmailAluno("aluno@test.com");
        a.setNota(4);
        a.setTitulo("Ótimo curso");
        a.setDescricao("Muito bom e didático");
        a.setCursoId("c1");
        return a;
    }

    private AvaliacaoRequest createRequest() {
        AvaliacaoRequest req = new AvaliacaoRequest();
        req.setEmailAluno("aluno@test.com");
        req.setNota(4);
        req.setTitulo("Ótimo curso");
        req.setDescricao("Muito bom e didático");
        req.setCursoId("c1");
        return req;
    }

    @Test
    void testCriarAvaliacao() throws Exception {
        Avaliacao a = createAvaliacao();
        AvaliacaoRequest req = createRequest();

        Mockito.when(service.criar(any(Avaliacao.class))).thenReturn(a);

        mockMvc.perform(post("/api/avaliacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nota").value(4))
                .andExpect(jsonPath("$.descricao").value("Muito bom e didático"));
    }

    @Test
    void testListarPorCurso() throws Exception {
        Avaliacao a1 = createAvaliacao();
        Avaliacao a2 = createAvaliacao();
        Mockito.when(service.listarPorCurso("c1")).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/api/avaliacoes/curso/c1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testExcluirAvaliacao() throws Exception {
        mockMvc.perform(delete("/api/avaliacoes/av1"))
                .andExpect(status().isNoContent());
    }
}
