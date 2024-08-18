package com.lastByte.Foro.Hub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    //Firma de token
    @Value("${JWT_SECRET}")
    private String privateKey;

    @Value("${JWT_USER_GENERATOR}")
    private String userGenerator;




    //CREAMOS EL TOKEN
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String usename = authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities() //Obtenemos todas las autorizaciones
                .stream()
                .map(GrantedAuthority::getAuthority) //lo cpnvertimos en un string
                .collect(Collectors.joining(",")); //lo separamos por ","

        try {
            String jwtToken = JWT.create()
                    .withIssuer(this.userGenerator) //Quien va a generar el token
                    .withSubject(usename) //A quien va a pertenecer el token
                    .withClaim("authorities", authorities)
                    .withIssuedAt(new Date()) // Fecha de creacion
                    .withExpiresAt(new Date(System.currentTimeMillis() + 7200000)) // Fecha de expiracion , la hora de el momento mas 2 horas en milisegundos
                    .withJWTId(UUID.randomUUID().toString()) //Le asignamos un ID ramdom a cada token
                    .withNotBefore(new Date(System.currentTimeMillis())) //A partir de cuando es valido el token , se puede hacer valido 2 horas despues
                    .sign(algorithm);

            return jwtToken;

        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error al crear el token!", exception);
        }
    }


    //Validador de token
    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT; //Si sale bien retorna el token decodificado

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token invalido , no autorizado");
        }

    }


    //Extraer el username
    public String extractUsername(@NotNull DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }


    //Extraer propiedad o cleim especifica
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    //Extraer todas las propiedades o claims
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

}
