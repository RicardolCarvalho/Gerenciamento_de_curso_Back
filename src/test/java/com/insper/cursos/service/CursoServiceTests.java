package com.insper.cursos.service;

import com.insper.cursos.config.Grupo2Config;
import com.insper.cursos.exception.BusinessException;
import com.insper.cursos.exception.ResourceNotFoundException;
import com.insper.cursos.model.Curso;
import com.insper.cursos.repository.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTests {

    @InjectMocks
    private CursoService service;

    @Mock
    private CursoRepository repo;

    @Mock
    private Grupo2Config.Grupo2Client grupo2Client;

    private Curso exemplo;

    @BeforeEach
    void setup() {
        exemplo = new Curso();
        exemplo.setId("c1");
        exemplo.setTitulo("Java Avançado");
        exemplo.setDescricao("Detalhes aprofundados");
        exemplo.setCargaHoraria(80);
        exemplo.setInstrutor("Prof. Souza");
        exemplo.setEmailCriador("criador@insper.br");
    }

    @Test
    void listar_quandoVazio_retornaListaVazia() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<Curso> result = service.listar();

        assertTrue(result.isEmpty());
        verify(repo).findAll();
    }

    @Test
    void listar_quandoExistem_retornaListaComEles() {
        when(repo.findAll()).thenReturn(List.of(exemplo, exemplo));

        List<Curso> result = service.listar();

        assertEquals(2, result.size());
        verify(repo).findAll();
    }

    @Test
    void criar_delegaParaRepositorySave() {
        when(repo.save(exemplo)).thenReturn(exemplo);

        Curso saved = service.criar(exemplo);

        assertSame(exemplo, saved);
        verify(repo).save(exemplo);
    }

    @Test
    void atualizar_quandoNaoExiste_lancaResourceNotFound() {
        when(repo.existsById("c1")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizar("c1", exemplo));
        verify(repo).existsById("c1");
        verify(repo, never()).save(any());
    }

    @Test
    void atualizar_quandoExiste_delegaParaSave() {
        when(repo.existsById("c1")).thenReturn(true);
        when(repo.save(exemplo)).thenReturn(exemplo);

        Curso updated = service.atualizar("c1", exemplo);

        assertSame(exemplo, updated);
        assertEquals("c1", exemplo.getId());
        verify(repo).existsById("c1");
        verify(repo).save(exemplo);
    }

    @Test
    void buscarPorId_quandoExiste_retornaCurso() {
        when(repo.findById("c1")).thenReturn(Optional.of(exemplo));

        Curso found = service.buscarPorId("c1");

        assertSame(exemplo, found);
        verify(repo).findById("c1");
    }

    @Test
    void buscarPorId_quandoNaoExiste_lancaResourceNotFound() {
        when(repo.findById("x")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId("x"));
        verify(repo).findById("x");
    }

    @Test
    void excluir_quandoSemMatriculas_deletaPorId() {
        when(grupo2Client.hasMatriculas("c1", "token")).thenReturn(Mono.just(false));
        doNothing().when(repo).deleteById("c1");

        assertDoesNotThrow(() -> service.excluir("c1", "token"));
        verify(grupo2Client).hasMatriculas("c1", "token");
        verify(repo).deleteById("c1");
    }

    @Test
    void excluir_quandoComMatriculas_lancaBusinessException() {
        when(grupo2Client.hasMatriculas("c1", "token")).thenReturn(Mono.just(true));

        assertThrows(BusinessException.class,
                () -> service.excluir("c1", "token"));
        verify(grupo2Client).hasMatriculas("c1", "token");
        verify(repo, never()).deleteById(any());
    }
}
