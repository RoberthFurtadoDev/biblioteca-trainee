package com.biblioteca.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI.
 * 
 * @Configuration - Marca como classe de configuração do Spring
 * 
 * Swagger/OpenAPI:
 * - Gera documentação automática da API
 * - Cria interface web interativa para testar endpoints
 * - Mostra todos os endpoints, parâmetros, respostas, etc
 * 
 * Acesso: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {
    
    /**
     * Configura as informações da API no Swagger.
     * 
     * @Bean - O Spring gerencia este objeto
     * OpenAPI - Objeto de configuração da documentação
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Biblioteca")
                        .version("1.0.0")
                        .description("""
                                API REST para gerenciamento de biblioteca.
                                
                                Funcionalidades:
                                - ✅ CRUD completo de livros
                                - ✅ Busca por autor, título, ano
                                - ✅ Controle de empréstimo/devolução
                                - ✅ Listagem de livros disponíveis
                                
                                Tecnologias:
                                - Java 17
                                - Spring Boot 3.2
                                - H2 Database (em memória)
                                - JPA/Hibernate
                                - Bean Validation
                                """)
                        .contact(new Contact()
                                .name("Roberth - Desenvolvedor Java")
                                .email("roberthfurtadodev@outlook.com")
                        )
                );
    }
}
