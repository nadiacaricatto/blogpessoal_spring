package com.generation.blogpessoal.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private static final String SECRET;
    static {
        // tenta carregar variáveis locais (para desenvolvimento)
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing() // evita erro se o arquivo .env não existir
                .load();

        // busca JWT_SECRET do .env OU das variáveis de ambiente do sistema
        
        SECRET = dotenv.get("JWT_SECRET", System.getenv("JWT_SECRET"));
    }
   
    private static final Duration EXPIRATION_DURATION = Duration.ofMinutes(60);
    
    private final SecretKey signingKey;
    
    public JwtService() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject().equals(userDetails.getUsername()) && 
               claims.getExpiration().after(new Date());
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(EXPIRATION_DURATION)))
            .signWith(signingKey)
            .compact();
    }
    
}

/*Linha 17: A classe foi anotada com @Component, indicando que se trata de uma Classe de Componente.
 * Isso significa que será gerenciada pelo Spring, podendo injetar e instanciar dependências em outras classes sempre que necessário.
 * Essa abordagem favorece a injeção de dependência automática, um dos pilares do Spring Framework.*/
