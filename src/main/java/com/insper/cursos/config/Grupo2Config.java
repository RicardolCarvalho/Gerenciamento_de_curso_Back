package com.insper.cursos.config;

import com.insper.cursos.dto.MatriculaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class Grupo2Config {

    @Value("${grupo2.client.base-url}")
    private String baseUrl;

    @Bean
    public WebClient grupo2WebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public Grupo2Client grupo2Client(WebClient grupo2WebClient) {
        return new Grupo2Client(grupo2WebClient);
    }

    public static class Grupo2Client {
        private final WebClient wc;

        public Grupo2Client(WebClient wc) {
            this.wc = wc;
        }

        public Mono<Boolean> hasMatriculas(String cursoId, String token) {
            return wc.get()
                    .uri("/curso/{id}", cursoId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(MatriculaDTO.class)
                    .hasElements()
                    .onErrorReturn(false);
        }

    }
}