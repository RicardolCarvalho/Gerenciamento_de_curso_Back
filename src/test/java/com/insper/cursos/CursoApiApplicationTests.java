// src/test/java/com/insper/cursos/CursoApiApplicationTests.java
package com.insper.cursos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CursoApiApplicationTests {

    /**
     * Testa se o contexto do Spring Boot carrega sem erros,
     * o que cobre o construtor da classe CursoApiApplication.
     */
    @Test
    void contextLoads() {
        // vazio: se chegar aqui sem exceção, o construtor foi coberto
    }

    /**
     * Invoca o método main da aplicação para cobrir main(String[]).
     */
    @Test
    void testMainRuns() {
        CursoApiApplication.main(new String[]{});
    }
}
