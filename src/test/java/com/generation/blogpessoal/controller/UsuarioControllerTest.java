package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import com.generation.blogpessoal.util.JwtHelper;
import com.generation.blogpessoal.util.TestBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//Random Port usa uma porta aleatório que está livre para rodar os testes - Se rodássemos na porta 8080 e a aplicação estiver rodando, ocorrerá um conflito de portas
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Indica que o ciclo de vida de cada teste seja feito por classe
@TestMethodOrder(MethodOrderer.DisplayName.class) //Indica a ordem que os testes serão executados através da propriedade display name (nome do método)

public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService; 
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static final String BASE_URL = "/usuarios"; //indica o caminho da requisição (RequestMapping de Usuario Controller)
	private static final String USUARIO = "root@root.com"; //indica qual usuario
	private static final String SENHA = "rootroot";

	@BeforeAll //Executa este método específico antes de rodar todos os testes, no caso: 
	void inicio() {
		usuarioRepository.deleteAll();
		usuarioService.cadastrarUsuario(TestBuilder.criarUsuario(null, "Root", USUARIO, SENHA));		
	}
	
	@Test
	@DisplayName ("01 - Deve Cadastrar um novo usuário com sucesso")
	//os testes devem ter nomes assertivos: 
	void deveCadastrarUsuario() {
	
		//Given - Dado que... - É o cenário do teste - Aqui criamos o objeto
		Usuario usuario = TestBuilder.criarUsuario(null, "Thuany", "thuany@email.com.br", "12345678");
		
		//When - É a ação principal do teste (Neste caso: fazer o cadastro) - Aqui enviamos o objeto para o corpo da requisição
		HttpEntity <Usuario> requisicao = new HttpEntity<Usuario>(usuario); //criando a requisição HTTP 
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange( //Configurando o corpo da requisição 
				BASE_URL + "/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
				
		//Then - Verifica se o resultado retornado está em conformidade - Se as afirmações forem verdadeiras, o teste passa; se uma ou mais forem falsas, não passa
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		
		
		
		
		
	}
	
	@Test
	@DisplayName ("02 - Não Deve Cadastrar um novo usuário com sucesso")
	//os testes devem ter nomes assertivos: 
	void naoDeveCadastrarUsuario() {
	
		//Given - Dado que... - É o cenário do teste - Aqui criamos o objeto
		Usuario usuario = TestBuilder.criarUsuario(null, "Rafaela Lemes", "rafa_lemes@email.com.br", "12345678");
		usuarioService.cadastrarUsuario(usuario);
		
		//When - É a ação principal do teste (Neste caso: fazer o cadastro) - Aqui enviamos o objeto para o corpo da requisição
		HttpEntity <Usuario> requisicao = new HttpEntity<Usuario>(usuario); //criando a requisição HTTP 
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange( //Configurando o corpo da requisição 
				BASE_URL + "/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
				
		//Then - Verifica se o resultado retornado está em conformidade - Se as afirmações forem verdadeiras, o teste passa; se uma ou mais forem falsas, não passa
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		
		}
				

	/*@Test
	@DisplayName ("03 - Deve Autalizar os dados do usuário com sucesso")
	//os testes devem ter nomes assertivos: 
	void DeveAtualizarUmUsuario() {
	
		//Given - Dado que... - É o cenário do teste - Aqui criamos o objeto
		Usuario usuario = TestBuilder.criarUsuario(null, "Rafaela Lemes", "rafa_lemes@email.com.br", "12345678");
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
		
		Usuario usuarioUpdate = TestBuilder.criarUsuario(usuarioCadastrado.get(). getId(), "Nadia Caricatto",
				"nadia@email.com.br", "abc12345");
		
		//When - É a ação principal do teste (Neste caso: fazer o cadastro) - Aqui enviamos o objeto para o corpo da requisição
		
		//Gerar o Token
		String token = JwtHelper.obterToken(testRestTemplate, USUARIO, SENHA);
		
		//Criar a Requisição com o Token:
		HttpEntity <Usuario> requisicao = JwtHelper.criarRequisicaoComToken(usuarioUpdate, token); //criando a requisição HTTP 
		
		//Enviar a Requisição PUT:
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange( //Configurando o corpo da requisição 
				BASE_URL + "/atualizar", HttpMethod.PUT, requisicao, Usuario.class);
				
		//Then - Verifica se o resultado retornado está em conformidade - Se as afirmações forem verdadeiras, o teste passa; se uma ou mais forem falsas, não passa
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());

	}*/

	@Test
	@DisplayName("3 - Deve atualizar os dados do usuário com sucesso")
	void DeveAtualizarUmUsuario() {
		//Given
		Usuario usuario = TestBuilder.criarUsuario(null, "Myriam", "myriam@email.com.br", "1234567894");
		Optional<Usuario> usuarioCadastrado =  usuarioService.cadastrarUsuario(usuario);
		
		Usuario usuarioUpdate = TestBuilder.criarUsuario(usuarioCadastrado.get().getId(),"Myriam Silva", "myriam@email.com", "987654321");
		
		//When
		
		//Gerar o Token
		String token = JwtHelper.obterToken(testRestTemplate, USUARIO, SENHA);
		
		//Criar a requisição com o token
		HttpEntity<Usuario> requisicao = JwtHelper.criarRequisicaoComToken(usuarioUpdate, token);
		
		//Fazer a requisição para atualizar
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange(BASE_URL + "/atualizar", HttpMethod.PUT,
				requisicao, Usuario.class);
		
		//Then
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
	}
	
	
	
	//Criar Método Listar Todas 
	
	//Criar Método Listar Por Id 
	
	
	
	
	
	
}
