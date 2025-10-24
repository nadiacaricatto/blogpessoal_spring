package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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

/*Query Methods: Métodos de Consulta Personalizados
 *Utilizados para criar consultas específicas com qualquer atributp da Clsse Associada a Interface Repositório.
 *São declaradas dentro da Repository e implementadas na Controller ou Service. 
 *
 *No exemplo acima, o método findAllByTituloContainingIgnoreCase(String titulo) é um Query Method personalizado.
 *Analisando:
 *- Find = SELECT: busca de registros no DB
 *- All = *: todos os registros
 *- By = WHERE: condição para a busca
 *- Titulo = coluna/atributo da tabela Postagem
 *- Containing = LIKE %?%: busca por similaridade (contém)
 *- IgnoreCase = IGNORE CASE: não diferenciar maiúsculas e minúsculas
 *
 *Então: 
 *- findAllBy (SELECT): Indica que queremos buscar todos os registros que correspondem a Título
 *- Titulo: Especifica o atributo da entidade Postagem que estamos consultando
 *- Containing: Indica que queremos buscar registros onde o título contém a string fornecida como parâmetro.
 *O Spring Data JPA interpreta esse nome de método e gera automaticamente a consulta SQL correspondente para buscar os dados no banco de dados.*/

	 public List <Postagem> findAllByTituloContainingIgnoreCase(String titulo); //Equivalente a: SELECT * FROM tb_postagens WHERE titulo LIKE "%?%";
	 
}


/*return postagemRepository.fundById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());*/

	
	
	
//A ? equivale ao valor que o usuário atribuir ao título (parâmetro da requisição)



/*@PostMapping
public ReponseEntity<Postagem> post(@RequestBody Postagem postagem){
	
	*/

/*A Repository trabalha com uma linguagem própria, chamada JPQL = Java Persistance Query Language */