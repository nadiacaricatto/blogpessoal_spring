package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

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
 *Isso permite que os desenvolvedores se concentrem na lógica de negócio, enquanto o framework cuida da infraestrutura.
 *Biblioteca Jackson: responsável pelo tipo jSON = retorno na saída dos dados*/

/*Agora vamos criar um objeto aqui na Controller, da Interface Repository, sem instanciar, poque estamos fazendo uma Injeção de Depedência,
usando a anotação @Autowired:*/

public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository; //(tipo e objeto) - Serão herdados todos os métodos do JpaRepository

/*Criando o método que vai trazer as informações (requisições) com Response Entitiy
*ResponseEntity é uma classe do Spring usada para personalizar a resposta HTTP.
*Ela permite controlar:
*- O status da resposta (200, 201, 404, 500, etc.)
*- O corpo da resposta (os dados em JSON, por exemplo)
*- O cabeçalhos (headers)*/
	
	@Autowired
	private TemaRepository temaRepository;
	
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

	@GetMapping("/{id}") //mapeando o id que vem na requisição - É uma variável de caminho, ou seja, uma variável que está no endereço da requisição
	
	//Aqui criamos o método getById, que vai buscar a postagem pelo ID:
	public ResponseEntity<Postagem> getById(@PathVariable Long id){ //Postagem é a entidade que vai ser retornada
		return postagemRepository.findById(id) //Postagem Repository é a injeção de dependência; findById é um método que já vem pronto no JpaRepository, e equivale a SELECT * FROM tb_postagens WHERE id = ?
	
	/*@PathVariable -> pega o valor do endereço (@GetMapping /id) e coloca na variável (Long ID) no método
	 * Ou seja: pathvariable indica o valor ID da URL que for buscado na requisição e o insere o valor na variável ID como parâmetro para o método getById
	 * Porque dá certo: porque ambas variáveis (do GetMapping e do método) têm o mesmo nome (id)
	 * Como é uma busca por objeto, só retorna um objeto (da classe Postagem) porque os IDs não se repetem*/
				
	/*Tratamento dos valores Nulos - Optional - Recurso Map 
	* Funciona como um laço condicional: verifica se houve retorno, se sim, retorna o valor com o código 200 (ok), se não, retorna o código 404 (not found)
	* O optional trabalha com expressões lambda (que são métodos sem corpo, ou seja, sem chaves {})*/

	//Como o retorno pode ser nulo, usamos o Optional para tratar o caso, fazendo: 
				.map(resposta -> ResponseEntity.ok(resposta)) //se encontrar, retorna o Status (código) 200 (ok) com a resposta (que é uma variável do tipo Postagem)
			
	//Caso o resultado não seja encontrado, usamos o orElse: 
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna o Status (código) 404 (not found)
	
	/*BUILD:
	 * É como se dissessemos ao Spring: "Crie uma resposta com o status 404 (Not Found), mas sem corpo nenhum — só o código de status”
	 * Porque é útil: O .build() é usado quando não há nada para retornar, e devolve uma resposta vazia (sem JSON).
	 * O front-end (recebe a resposta e verifica o status code:
	 * - Se for 200, mostra os dados na tela.
	 * - Se for 404, mostra uma mensagem tipo “Postagem não encontrada”, permitindo tratar os erros de forma amigável (exibir erro, alerta, redirecionar, etc.), sem travar a aplicação.*/
	
	
	}
	@GetMapping ("/titulo/{titulo}") //titulo é como um label que diz: "digita o título que você quer procurar; e {titulo} é a variável em si
	//Chamando o método criado na Repository:
	public ResponseEntity<List<Postagem>> getAllByTitulo(@PathVariable String titulo){ //@pathvariable para pegar o valor que vem na requisição e colocar na variável título
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo)); //Return: ResponseEntity ok (200) com a lista de postagens que encontrar
}
	
	//Criando o Método Post - Criar nova postagem
	
	@PostMapping 
	public ResponseEntity <Postagem> post(@Valid @RequestBody Postagem postagem){
		
		if(temaRepository.existsById(postagem.getTema().getId())) { //Tema está dentro de postagem, então primeiro acessamos o objeto Postagem, pegamos o objeto Tema e, por fim, pegamos a propriedade ID
			
			postagem.setId(null); //O ID precisa estar com o valor nulo - porque é o banco que vai gerar o ID automaticamente
		
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem)); //Método de Persistência: indica que o objeto foi persistido (salvo) -> equivalente a INSERT INTO tb_postagens (titulo, texto) VALUES (?, ?)
			 //Retorna o Status (código) 201 (Created) com o corpo da postagem que foi salva
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema não existe!", null); //Se o tema não existir -> Bad Request 400 "O tema não existe"
		//Ao inserir Null, ele só exibe o código do Status e a mensagem entre "" - se não colocássemos o Null, ele exibiria todo o código de erro
	}
	
	/*Destrinchando: 
	 * Status(HttpStatus.CREATED) > indica a gravação do objeto no sistema (201 CREATED)
	 * .body > o corpo da requisição terá o objeto postagem que foi salvo no banco de dados
	 * postagemRepository > objeto da Interface Repository que faz a persistência (salva) dos dados
	 * .save(postagem) > método que salva o objeto postagem no banco de dados*/
		
	/*Chovendo no molhado só pra fixar: 
	 * Tipo do método > Public
	 * Tipo ResponseEntity > Responde a requisição HTTP
	 * <Postagem> > Objeto tipo postagem que vai ser retornado
	 * Post > Nome do método
	 * Postagem > Classe
	 * postagem > objeto da Classe Postagem
	 * RequestBody -> pegar o objeto postagem que está no corpo da requisição HTTP (em JSON)
	 * @Valid > anotação que aplica todas as regras de validação definidas na Model*/
	 
	/*O objeto terá basicamente 2 infos: título e texto
	 * A Hora/Data será fornecida pelo sistema, e o ID será gerado automaticamente pelo banco de dados*/
			
	/*@PutMapping //Atualizar uma postagem existente 
	public ResponseEntity <Postagem> put(@Valid @RequestBody Postagem postagem){
		return postagemRepository.findById(postagem.getId()) //A postagem já exite e já tem ID atribuído, então a busca será pelo ID do objeto postagem
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem))) //Exibir o resultado da atualização no corpo da resposta
		
		//Caso contrário:
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());*/
	
	//Refazendo o Put - Trocando .map por ExistsById: 
	
	@PutMapping //Atualizar uma postagem existente 
	public ResponseEntity <Postagem> put(@Valid @RequestBody Postagem postagem){
		
		if(postagemRepository.existsById(postagem.getId())) {
			
			if(temaRepository.existsById(postagem.getTema().getId())) { 
			
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
			}
	
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema não existe!", null);
		
		}

		return ResponseEntity.notFound().build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT) // Define o status de resposta como 204 (No Content)
	@DeleteMapping("/{id}") // Mapeia requisições DELETE com um ID na URL para esse método
	
	public void delete(@PathVariable Long id) { // Extrai o ID da URL e define o método delete que não retorna nada
		Optional<Postagem> postagem = postagemRepository.findById(id); // Busca a postagem pelo ID
		if (postagem.isEmpty()) // Verifica se a postagem existe
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); // Se a postagem não existir, lança uma exceção com status 404 (Not Found)
		postagemRepository.deleteById(id); // Se existir, deleta a postagem pelo ID
		// equivalente ao DELETE FROM tb_postagens WHERE id = ?;
	}
}





































	
//A Repository define um contrato (métodos) para as operações de acesso a dados, mas não implementa a lógica desses métodos, ou seja, não especifica como eles serão implementados 

























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