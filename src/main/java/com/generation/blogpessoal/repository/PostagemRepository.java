package com.generation.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.generation.blogpessoal.model.Postagem;

/*A interface Repository é a responsável por criar os métodos que serão transformados pelo
 *Hibernate em instruções SQL, ou seja, se comunicando com o banco de dados*/
 
/*#1: Indicando a Repository com qual classe ela irá trabalhar -> criação de Herança
 *Classe model: Postagem; O Atributo: Long (porque id nesse caso é do tipo long
 *O JpaRepository é uma interface genérica da biblioteca Spring Data JPA, 
 *que já herda (ou seja: já "vem pronta") todos os métodos que seriam criados manualmente, como: 
 *save() → salvar ou atualizar dados
 *findAll() → buscar tudo
 *findById() → busca específica
 *deleteById() → deletar
 *O hibernate lê os métodos criados e aplica a instrução SQL no banco de dados.
 *Devemos colocar o JpaRepository sempre que trabalharmos com banco de dados?
 *Resposta: quase sempre que criarmos um repositório (ou seja, uma camada de acesso a dados) no Spring.
 *Mais precisamente: somente quando precisarmos que o Spring cuide automaticamente das operações CRUD para uma entidade JPA (hibernate).
 *Por que passar dois tipos: Postagem e Long?
 *Resposta: essa é a assinatura genérica do JpaRepository<T, ID>, sendo:
 *T → tipo da entidade (a classe model, mapeada com @Entity, ex: Postagem)
 *ID → é o tipo do atributo da chave primária dessa entidade (Long, UUID, Integer, etc.)
 *Então, é nesse momento (JpaRepository<Postagem, Long>) que  indicamos a chave primária e o tipo, 
 *onde informamos o repositório qual é a entidade que ele vai manipular (Classe Model Postagem)
 *e qual é o tipo do identificador, nesse caso, um Long, porque o atributo id foi declarado assim na classe Postagem.*/


public interface PostagemRepository extends JpaRepository<Postagem, Long> {
/*Traduzindo: toda e qualquer operação que for feita utilizando Postagem Repository, deverá utilizar a tabela tb_postagens, 
	definida na classe Postagem, e vai utilizar o atributo long (id) como chave primária.*/
}

/*public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);
	return postagemRepository.fundById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
}// SELECT * FROM tb_ostagens WHERE titulo LIKE "%?%";
//A ? equivale ao valor que o usuário atribuir ao título (parâmetro da requisição)



@PostMapping
public ReponseEntity<Postagem> post(@RequestBody Postagem postagem){
	
	*/
