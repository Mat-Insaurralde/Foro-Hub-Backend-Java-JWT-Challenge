package com.lastByte.Foro.Hub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration  //Spring escanea primero al ser una configuracion
@EnableWebSecurity //indica que vamos a sobreescribir el metodo de autenticacion con el que queremos
public class SecurityConfig {



    private final JwtTokenValidatorFilter tokenValidatorFilter;

    @Autowired
    public SecurityConfig(JwtTokenValidatorFilter tokenValidatorFilter) {
        this.tokenValidatorFilter = tokenValidatorFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable()) //es para evitar suplantacion de identidad

                //lo deshabilito porque vamos a usar aut token
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll() //Permite  ingresar a los POST /auth
                        .requestMatchers("/swagger-ui.html","/v3/api-docs/**","/swagger-ui/**").permitAll()

                        .requestMatchers("/api/role/**").hasRole("ADMIN")
                        .requestMatchers("/api/permiso/**").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/api/cursos/**").permitAll()
                        .requestMatchers("/api/cursos/**").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET,"/api/topicos/**").permitAll()
                        .requestMatchers("/api/topicos/**").authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/respuesta/**").permitAll()
                        .requestMatchers("/api/respuesta/**").authenticated()

                        .anyRequest().denyAll()

                ) //Es un poco mas seguro que authenticated
                        //.authenticated()) //Todos los demas request deben ser autenticados
                //Agrega mi filtro antes de que lanze su filtro
                .addFilterBefore(tokenValidatorFilter,
                        UsernamePasswordAuthenticationFilter.class )//Valida que el usuario tenga una sesion iniciada
                .build();
    }













   //Autentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

   ///Proveedor  de la autenticacion // Con este provider podemos conectarnos a una BD //POdemos usar otro provider para authh2
   @Bean
   public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl detailsService){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setPasswordEncoder(passwordEncoder());
       provider.setUserDetailsService(detailsService);
       return provider;
   }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
