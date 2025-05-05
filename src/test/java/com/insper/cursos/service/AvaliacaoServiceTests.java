package com.insper.cursos.service;

import com.insper.cursos.exception.ResourceNotFoundException;
import com.insper.cursos.model.Avaliacao;
import com.insper.cursos.repository.AvaliacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvaliacaoServiceTests {

    @InjectMocks
    private AvaliacaoService service;

    @Mock
    private AvaliacaoRepository repo;

    private Avaliacao exemplo;

    @BeforeEach
    void setup() {
        exemplo = new Avaliacao();
        exemplo.setId("av1");
        exemplo.setEmailAluno("aluno@test.com");
        exemplo.setNota(5);
        exemplo.setTitulo("Excelente");
        exemplo.setDescricao("Conteúdo muito relevante");
        exemplo.setCursoId("c1");
    }

    @Test
    void listarPorCurso_quandoVazio_retornaListaVazia() {
        when(repo.findByCursoId("c1")).thenReturn(Collections.emptyList());

        List<Avaliacao> result = service.listarPorCurso("c1");

        assertTrue(result.isEmpty());
        verify(repo).findByCursoId("c1");
    }

    @Test
    void listarPorCurso_quandoExistem_retornaListaComEles() {
        when(repo.findByCursoId("c1")).thenReturn(List.of(exemplo, exemplo));

        List<Avaliacao> result = service.listarPorCurso("c1");

        assertEquals(2, result.size());
        verify(repo).findByCursoId("c1");
    }

    @Test
    void criar_delegaParaRepositorySave() {
        when(repo.save(exemplo)).thenReturn(exemplo);

        Avaliacao saved = service.criar(exemplo);

        assertSame(exemplo, saved);
        verify(repo).save(exemplo);
    }

    @Test
    void excluir_quandoExiste_deletaPorId() {
        when(repo.existsById("av1")).thenReturn(true);
        doNothing().when(repo).deleteById("av1");

        assertDoesNotThrow(() -> service.excluir("av1"));
        verify(repo).existsById("av1");
        verify(repo).deleteById("av1");
    }

    @Test
    void excluir_quandoNaoExiste_lancaResourceNotFound() {
        when(repo.existsById("x")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.excluir("x"));
        verify(repo).existsById("x");
        verify(repo, never()).deleteById(any());
    }
}
