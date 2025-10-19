package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

/*REST: Representational State Transfer, ou, traduzindo: Transferência Representacional de Estado.
REST é um tipo de arquitetura usado para contrução de APIs que se comunicam pela internet usando o protocolo HTTP.
Quando criamos uma API REST, estamos "dizendo": "Meu back-end vai expor recursos (dados, entidades, objetos) através de URLs,
e qualquer cliente pode interagir com eles usando os métodos HTTP.”
RECURSO - CONCEITO: qualquer coisa que pode ser representada e manipulada — uma postagem, um usuário, um produto, etc.
e cada recurso tem um endpoint (um endereço único na web), e cada um desses endpoints pode ser acessado e manipulado usando os verbos HTTP.*/

/*Explicando: 
 *Anotação @RestController -> marca uma classe como um controlador REST.
 *Ou seja: é o ponto onde o Spring vai receber requisições HTTP (como GET, POST, PUT, DELETE) e devolver respostas.
 *Ela faz duas coisas ao mesmo tempo:
 *1 - Registra a classe como um componente do Spring (ou seja, o Spring passa a gerenciar essa classe);
 *2 - Garante que o retorno dos métodos será convertido automaticamente em JSON (ou XML, se configurado).
 *Anotação @Requestpping - > usada para definir o caminho base (endpoint) que vai ser associado à classe ou a um método
 *Ou seja: cria o “endereço” da API.
 *Pode ser usada de duas formas:
 *1 - No nível da classe: define o caminho base pra todos os métodos
 *2 - No nível do método: define rotas específicas dentro desse caminho base.*/

/* O back está em um servidos, e o front está em outro. Nossa API só aceitaria requisições do mesmo servidor, então precisamos permitir 
nossa classe Controller receba requisições de outros servidores. Para isso, usamos a anotação @CrossOrigin, que permite requisiçõesde outras origens*/

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
/*O que isso significa: permitir requisições de qualquer origem (origins="*") e com qualquer cabeçalho (allowedHeaders)
 * Liberar cabeçalho: requisições HTTP podem incluir cabeçalhos personalizados (headers), necessários para autenticação. 
 * Cabeçalho é uma parte da requisição HTTP que carrega informações adicionais sobre a requisição ou cliente (que está requerendo).
 * Se subtituíssemos o * pelo endereço do front-end, estaríamos dizendo: "Só aceite requisições desse endereço específico".*/

/*Inversão de Controle/Inversão de Responsabilidade: 
 *No desenvolvimento padrão, o programador é responsável por criar (instanciar) e gerenciar os objetos e suas dependências.
 *Com a Inversão de Controle, essa responsabilidade é transferida para um framework (no nosso caso, o Spring).
 *O Spring cria, configura e gerencia o ciclo de vida dos objetos automaticamente.
 *A Inversão é implementada através de Injeção de Dependência (DI Containers).
 *Esses contêineres são responsáveis por instanciar os objetos, resolver suas dependências e injetá-los onde forem necessários.
 *Isso permite que os desenvolvedores se concentrem na lógica de negócio, enquanto o framework cuida da infraestrutura.*/

/*Agora vamos criar um objeto aqui na Controller, da Interface Repository, sem instanciar, poque estamos fazendo uma Injeção de Depedência,
usando a anotação @Autowired:*/

public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository; //(tipo e objeto) - Serão herdados todos os métodos do JpaRepository

//Criando o método que vai trazer as informações (requisições) - Responde Entitiy é, basicamente, uma resposta HTTP 
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
		
/*Porque lista: porque pode ter nenhuma, uma ou N - então a interface List cria a lista Postagem 
 * Dois sinais de maior >>: #1 tipo que vai retornar; #2 A lista que vai ser retornada
 * OK: porque se encontrar, ele retorna ok 200;
 * Dentro dos () vai o método responsável por trazer todas as postagens (o método definido em postagemRespository): findAll
 * 
 * FINDALL = SELECT * FROM TB_POSTAGENS --> Porque dá certo: este método executa essa instrução dentro do banco,
 * e ele consegue porque a Interface Reposiroty, que recebe a classe Model Postagem, que por sua vez, define a tabela tb_postagens*/
	
	}
}
	
	





//@GetMapping ("/{id}")
//public ResponseEntity<Postagem> getById(@PathVariable Long id)










/* Exemplos das Anotações: 
 * @RestController
 * public class PostagemController {

    @GetMapping("/postagens")
    public String listar() {
        return "Olá, mundo das postagens!";
        
  *O que o Spring faz: 
  * - Escutar qualquer requisição HTTP GET para /postagens;
  * – Executar o método listar();
  * – Devolver o texto "Olá, mundo das postagens!" como resposta HTTP.
  * 
  * @RequestMapping na Classe: 
  * @RequestMapping("/postagens")
  * public class PostagemController {
    
    @GetMapping
    public List<Postagem> listarTodas() {
        // Retorna todas as postagens
    }
}
  *O caminho completo da requisição será:
  *GET /postagens
  *
  *@RequestMapping no Método:
  * @RestController
	@RequestMapping("/postagens")
	public class PostagemController {
    
    @GetMapping("/{id}")
    public Postagem buscarPorId(@PathVariable Long id) {
        // Busca uma postagem pelo ID
    }

    @PostMapping
    public Postagem criar(@RequestBody Postagem postagem) {
        // Cria nova postagem
    }
}
  *Nesse caso, o caminho da requisição será:
   GET /postagens/1 → busca a postagem de ID 1
   POST /postagens → cria uma nova postagem

RELEMBRANDO:
JSON é um formato de texto estruturado, usado pra enviar e receber dados entre sistemas.
- É independente de linguagem (apesar de ter nascido do JavaScript)
- É fácil de ler, tanto por humanos quanto por máquinas
- No Spring Boot, ele é o idioma padrão da comunicação entre o servidor (Java) e o cliente (navegador, app, etc)







*/