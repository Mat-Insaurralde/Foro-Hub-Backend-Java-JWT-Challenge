package com.lastByte.Foro.Hub.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //Aplica la configuración CORS a todas las rutas de tu aplicación.
               .allowedOrigins("*") //EJ//https:.......
              //  .allowedOriginPatterns()
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") //Permite todos los headers en las solicitudes CORS.
                .allowCredentials(false) //Permite enviar cookies en solicitudes CORS (si tu aplicación las usa).
                .maxAge(3600); //Establece cuánto tiempo el navegador debe cachear la respuesta pre-vuelo CORS.
    }





}
