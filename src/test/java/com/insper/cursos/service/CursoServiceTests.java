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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
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
    private String token;

    @BeforeEach
    void setup() {
        exemplo = new Curso();
        exemplo.setId("abc");
        exemplo.setTitulo("Java Básico");
        exemplo.setDescricao("Aprenda Java");
        exemplo.setCargaHoraria(20);
        exemplo.setInstrutor("João");
        exemplo.setEmailCriador("joao@ex.com");

        token = "dummy-token";
    }

    @Test
    void listar_quandoVazio_retornaListaVazia() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        var result = service.listar();

        assertTrue(result.isEmpty());
        verify(repo).findAll();
    }

    @Test
    void listar_quandoExistemCursos_retornaListaComEles() {
        when(repo.findAll()).thenReturn(Arrays.asList(exemplo, exemplo));

        var result = service.listar();

        assertEquals(2, result.size());
        verify(repo).findAll();
    }

    @Test
    void buscarPorId_quandoExiste_retornaCurso() {
        when(repo.findById("abc")).thenReturn(Optional.of(exemplo));

        var found = service.buscarPorId("abc");

        assertSame(exemplo, found);
        verify(repo).findById("abc");
    }

    @Test
    void buscarPorId_quandoNaoExiste_lancaResourceNotFound() {
        when(repo.findById("xyz")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId("xyz"));
        verify(repo).findById("xyz");
    }

    @Test
    void criar_delegaParaRepositorySave() {
        when(repo.save(exemplo)).thenReturn(exemplo);

        var saved = service.criar(exemplo);

        assertSame(exemplo, saved);
        verify(repo).save(exemplo);
    }

    @Test
    void atualizar_quandoExiste_retornaSalvoComMesmoId() {
        when(repo.existsById("abc")).thenReturn(true);
        when(repo.save(exemplo)).thenReturn(exemplo);

        var updated = service.atualizar("abc", exemplo);

        assertSame(exemplo, updated);
        assertEquals("abc", exemplo.getId());
        verify(repo).existsById("abc");
        verify(repo).save(exemplo);
    }

    @Test
    void atualizar_quandoNaoExiste_lancaResourceNotFound() {
        when(repo.existsById("xyz")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizar("xyz", exemplo));
        verify(repo).existsById("xyz");
        verify(repo, never()).save(any());
    }

    @Test
    void excluir_quandoNaoHaMatriculas_deletaPorId() {
        when(grupo2Client.hasMatriculas(eq("abc"), eq(token)))
                .thenReturn(Mono.just(false));
        doNothing().when(repo).deleteById("abc");

        assertDoesNotThrow(() -> service.excluir("abc", token));

        verify(grupo2Client).hasMatriculas("abc", token);
        verify(repo).deleteById("abc");
    }

    @Test
    void excluir_quandoHaMatriculas_lancaBusinessException() {
        when(grupo2Client.hasMatriculas(eq("abc"), eq(token)))
                .thenReturn(Mono.just(true));

        assertThrows(BusinessException.class,
                () -> service.excluir("abc", token));

        verify(grupo2Client).hasMatriculas("abc", token);
        verify(repo, never()).deleteById(any());
    }

    @Test
    void excluir_quandoClienteReativoRetornaNull_lancaNPE() {
        // simula cliente mal configurado retornando null
        when(grupo2Client.hasMatriculas(eq("abc"), eq(token)))
                .thenReturn(null);

        assertThrows(NullPointerException.class,
                () -> service.excluir("abc", token));

        verify(grupo2Client).hasMatriculas("abc", token);
    }
}
