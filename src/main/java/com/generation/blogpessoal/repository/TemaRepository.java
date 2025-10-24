package com.generation.blogpessoal.repository; 

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param; //

import com.generation.blogpessoal.model.Tema;// Importando a classe Tema da model



public interface TemaRepository extends JpaRepository<Tema, Long>{ // Define a interface PostagemRepository que estende JpaRepository 
	
	// Query Methods
	public List<Tema> findAllByDescricaoContainingIgnoreCase(@Param("descricao")String descricao); // Método para buscar postagens por descricao, ignorando maiúsculas e minúsculas

}