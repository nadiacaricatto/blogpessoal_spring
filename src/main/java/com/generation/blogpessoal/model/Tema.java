package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_temas") // Define o nome da tabela que será criada no db
public class Tema {
	
@Id  // PRIMARY KEY(id) // indica que o atributo id é a chave primária
@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT do DB
	private Long id;

@Column(length = 1000) // Define o tamanho máximo do campo no banco de dados
@NotBlank(message = "O atributo Descrição é obrigatório!") // Não pode ser nulo ou vazio
@Size(min = 10, max = 100, message = "O atributo Descricao deve conter no mínimo 10 e no máximo 100 caracteres") 
private String descricao; 

//Configurando a relação Tema - Postagem:

@OneToMany(fetch = FetchType.LAZY, mappedBy = "tema", cascade = CascadeType.REMOVE)
/*Relação do tipo bidirecional -> Um tema para muitas postagens
 *Fetch: tipo de carregamento de dados -> significa que os dados só serão carregados quando forem  explicitamente requisitados pela primeira vez ao invés de serem carregados com a pesquisa principal (EAGER)
 *MappedBy: indica a chave estrangeira, no caso, o objeto "tema" da classe Postagem.model
 *Cascade: CascadeType.REMOVE → Controla a forma que as alterações em um objeto são refletidas - Neste caso: 
 *indica que quando um Tema for deletado (alterado) todas as Postagens associadas a esse Tema também serão removidas do db*/

@JsonIgnoreProperties(value = "tema", allowSetters = true) //Ignora o get (que equivale a escrita) sem ignorar os Sets, que servirão para preencher a lista Postagem
private List<Postagem> postagem;
/*Explicando: 
 * value = "tema" → indica o nome do atributo na classe Postagem que referencia o Tema
 * allowSetters = true → permite que o Jackson utilize os setters durante a desserialização
 * Setters são métodos que atribuem valores aos atributos de uma classe, e servem para modificar os valores dos atributos de um objeto,
 * neste caso, os valores dos atributos da classe Tema */

public String getDescricao() {
	return descricao;
}

public void setDescricao(String descricao) {
	this.descricao = descricao;
}

public List<Postagem> getPostagem() {
	return postagem;
}

public void setPostagem(List<Postagem> postagem) {
	this.postagem = postagem;
}

public void setId(Long id) {
	this.id = id;
}

public Long getId() {
	return this.id;
	
	
}

}
	
	