package com.generation.blogpessoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BlogpessoalApplication {

	public static void main(String[] args) {
		try {
            // Carrega .env apenas se existir (modo local)
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

            // Adiciona variáveis locais se não existirem no ambiente
            dotenv.entries().forEach(entry -> {
                if (System.getenv(entry.getKey()) == null) {
                    System.setProperty(entry.getKey(), entry.getValue());
                }
            });

            System.out.println("✅ Variáveis carregadas (modo local)");
        } catch (Exception e) {
            System.out.println("⚠️ Nenhum arquivo .env encontrado (modo produção)");
        }

		
		SpringApplication.run(BlogpessoalApplication.class, args);
	}

}