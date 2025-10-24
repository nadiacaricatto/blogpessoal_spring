package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/*2: Classe e criação de tabelas - Explicitando para o Spring que essa classe gerará tabelas dentro do banco de dados
 * Antes da definição da classe, inserimos a anotação @Entity. O conceito e a explicação: 
 * Entidade é uma classe Java que representa uma tabela no banco de dados, e 
 * cada objeto dessa classe representa uma linha (ou registro) nessa tabela.
 * Então @Entity significa definir a classe como um tipo entidade que vai gerar uma tabela dentro do banco de dados.*/

@Entity

//Logo abaixo da anotação @Entity, inserimos a anotação @Table para indicar o nome da tabela que será criada no DB

@Table(name = "tb_postagens") // name é a propriedade que define o "tb_postagens"

/*
 * Se não houvesse a anotação @Table, o Spring criaria a tabela com o nome da
 * classe [Postagem] O nome em si não está errado, mas estaria em desacordo com
 * as regras de sintaxe do SQL
 * 
 * @Table equivale a escrever no DB CREATE TABLE tb_postagens(
 */

public class Postagem {

	/*
	 * #3: Configurando atributos Inserindo a anotação @Id: indica que este atributo
	 * em questão será a chave primária (PRIMARY KEY (id)) A origem da anotação é
	 * Jakarta Persistance
	 */

	@Id // Euivalente a PRIMARY KEY (id) no MySQL

	// Habilitando o auto incremento para geração de id (números sequenciados)
	// automaticamente:*/

	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT do MySQL

	// #1: Criando a Classe
	/*
	 * Iniciamos a classe com 4 atributos - conforme os relacionamentos forem
	 * criados, o número de atributos irá aumentar
	 */

	private Long id; // Long é com L maiúsculo por se referir classe Long / Wrapper

	/*
	 * #4: REGRAS DE VALIDAÇÃO: Criar regras de validação significa estabelecer
	 * critérios que os dados precisam cumprir antes de serem aceitos, salvos no
	 * banco ou processados pelo sistema. Essas regras ajudam a garantir
	 * integridade, consistência e qualidade dos dados. As validações são
	 * configuradas através de anotações (@)
	 *
	 * IMPORTANTE: As regras de validação devem ser refletidas pelo DB. Para isso,
	 * utilizamos a anotação @column e, neste caso, onde estamos tratando o tamanho
	 * dos atributos, entre () colocamos a propriedade length
	 *
	 * RELEMBRANDO: Na programação orientada a objetos (OO), atributo é o nome
	 * técnico para as variáveis que pertencem a uma classe.
	 *
	 * Porque o @Size sozinho não é suficiente: O @Size(min = 10, max = 1000) não
	 * altera o banco de dados, ele é apenas uma regra de validação no nível da
	 * aplicação Java — o Hibernate valida o valor antes de salvar, mas o banco de
	 * dados em si não sabe nada sobre isso. Se não houve a anotação @Column(length
	 * = 1000), o banco pode acabar criando o campo como VARCHAR(255), que é o
	 * padrão, e mesmo que seu @Size aceite até 1000 caracteres, o banco vai truncar
	 * ou rejeitar os valores acima de 255, porque as regras não estarão
	 * “alinhadas”.
	 *
	 * | Camada | Anotação | Função | | -------------- | --------------------- |
	 * ------------------------------------------------ | | Aplicação Java |
	 * `@Size`, `@NotBlank` | Valida os dados antes de aceitar/salvar/processar| |
	 * Banco de Dados | `@Column(length = X)` | Define limites físicos na tabela |
	 */

	@Column(length = 100)
	@NotBlank(message = "O atributo título é obrigatório!") // O atributo não pode ser nulo, vazio ou em branco
	@Size(min = 5, max = 100, message = "O atributo título deve conter no mínimo 5 e no máximo 100 caracteres.") /* Define o tamanho mínimo e máximo do atributo*/																										// tamanho
	private String titulo;

	@Column(length = 1000)
	@NotBlank(message = "O atributo texto é obrigatório!")
	@Size(min = 10, max = 1000, message = "O atributo texto deve conter no mínimo 10 e no máximo 1000 caracteres.")
	private String texto;

	@UpdateTimestamp // atualiza automaticamente a data tanto na hora da criação como quando houver alguma alteração
	private LocalDateTime data; // LocaDateTime é uma classe que precisa ser importada - Guarda a hora em que a postagem é criada/atualizada

	// O @CreateTimestamp guarda a data e hora de criação da postagem

	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;

	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	// Não criaremos método construtor, quem cria é o próprio spring internamente -
	// e não na classe. Nós criamos
	// apenas os métodos get/set

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	/*public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}*/

	public void setId(Long id) {
		this.id = id;
	}

	// O arquivo pom.xml tem que estar visível na raiz para que possa ser usado no
	// momento do deploy

	/*
	 * Observação a respeito do Auto Increment:
	 * 
	 * Strategy Identity é a forma de definir qual é a estratégia que o JPA (Java
	 * Persistence API) vai usar pra gerar o valor da chave primária — ou seja, a
	 * forma como o id será criado automaticamente quando você salvar um novo
	 * registro. Existem várias estratégias possíveis, e o GenerationType.IDENTITY é
	 * uma delas. No caso do IDENTITY, o JPA delega essa responsabilidade para o
	 * banco de dados, que usa um campo com auto-incremento (como o AUTO_INCREMENT
	 * do MySQL) pra gerar o próximo valor da sequência. Então, o fluxo é assim:
	 * 
	 * Você salva um novo objeto no banco (o JPA não vai gerar o id) > O banco
	 * (MySQL) gera o próximo número automaticamente > O número é recuperado pelo
	 * JPA, que o associa ao objeto em memória.
	 * 
	 * Outras estratégias:
	 * 
	 * -> AUTO: o JPA escolhe automaticamente a melhor estratégia dependendo do
	 * banco.
	 * 
	 * -> SEQUENCE: usa uma sequence (muito comum em bancos como PostgreSQL e
	 * Oracle).
	 * 
	 * -> TABLE: cria uma tabela auxiliar só pra controlar a geração de IDs.
	 * 
	 * No MySQL IDENTITY é a escolha clássica
	 */

	/*
	 * CURIOSIDADE: A palavra “IDENTITY” vem de um conceito que existe desde antes
	 * do JPA, direto do mundo dos bancos de dados relacionais. Em bancos como SQL
	 * Server, MySQL e DB2, um “identity column” (ou coluna identidade) é uma coluna
	 * que tem a capacidade de gerar automaticamente seu próprio valor único,
	 * normalmente com auto-incremento. Ou seja, ela é responsável por definir a
	 * identidade de cada linha na tabela — daí o nome IDENTITY.
	 */

}

/*
 * O que o JPA faz é mapear essa classe Java (Postagem) para uma tabela do banco
 * de dados (tb_postagem). Isso significa que: O nome da classe → vira o nome da
 * tabela (ou o que você indicar em @Table); Os atributos → viram colunas da
 * tabela; Cada objeto da classe → representa um registro na tabela.
 */
