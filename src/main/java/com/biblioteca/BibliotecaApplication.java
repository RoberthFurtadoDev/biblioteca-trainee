package com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * 
 * @SpringBootApplication - Combina 3 anotações:
 * 1. @Configuration - Marca como classe de configuração
 * 2. @EnableAutoConfiguration - Habilita configuração automática
 * 3. @ComponentScan - Escaneia pacotes em busca de @Component, @Service, etc
 * 
 * O Spring Boot:
 * - Configura servidor Tomcat automaticamente
 * - Configura JPA/Hibernate automaticamente
 * - Detecta todas as classes anotadas (@Controller, @Service, @Repository)
 * - Injeta dependências automaticamente
 */
@SpringBootApplication
public class BibliotecaApplication {
    
    /**
     * Metodo main - Ponto de entrada da aplicação.
     * 
     * SpringApplication.run():
     * 1. Inicia o servidor embutido (Tomcat)
     * 2. Configura o Spring Context
     * 3. Injeta todas as dependências
     * 4. Inicia a aplicação
     */
    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
        
        System.out.println("\n========================================");
        System.out.println("API Biblioteca está rodando!");
        System.out.println("========================================");
        System.out.println("API: http://localhost:8080/api/livros");
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("Console H2: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}
