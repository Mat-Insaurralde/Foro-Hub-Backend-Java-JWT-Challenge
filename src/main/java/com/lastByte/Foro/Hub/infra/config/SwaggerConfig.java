package com.lastByte.Foro.Hub.infra.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Foro Hub API")
                        .version("1.0")
                        .description("API para la gestión de cursos y tópicos")
                        .contact(new Contact()
                                .name("Javier Insaurralde")
                                .email("javiermatias115@gmail.com")
                                .url("https://www.linkedin.com/in/javier-matias-insaurralde-3aa783274/")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("forohub-public")
                .pathsToMatch("/**")
                .build();
    }
}