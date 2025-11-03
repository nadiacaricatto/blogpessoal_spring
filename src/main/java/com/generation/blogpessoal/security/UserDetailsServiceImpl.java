package com.generation.blogpessoal.security;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username == null || username.trim().isEmpty()) {
			throw new UsernameNotFoundException("Parece que o e-mail inserido não é válido ou está em branco! Verifique as informações e tente novamente.");
		}
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(username);

		if (usuario.isPresent()) {
			return new UserDetailsImpl(usuario.get());
		}else {
			throw new UsernameNotFoundException("Usuário não encontrado: " + username);
		}
			
	}
}