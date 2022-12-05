package com.lojaGames.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lojaGames.model.Usuario;
import com.lojaGames.model.UsuarioLogin;
import com.lojaGames.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//cadastrar usuario
	public Optional <Usuario> cadastrarUsuario(Usuario usuario){
		
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		
		return Optional.of(usuarioRepository.save(usuario)); //pegando o resutano e salvando no objeto. 
		
	}
	
	
	//atualizar usuario
	public Optional <Usuario> atualizarUsuario(Usuario usuario){
		
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())	{
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			return Optional.ofNullable(usuarioRepository.save(usuario)); 
			//se retornar um of vc tem certeza que nao vai ser nulo
			//ofNullLabel voce informa que ele seja nulo
				
		}
		return Optional.empty();
		
	}
	
	
	//login
	
	public Optional <UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
		
		// procurar se ele existe
		Optional <Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if (usuario.isPresent()) {
			//verificar senha digitada esta no banco de dados do usuario
			if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				
				//pegando info do banco e gravando o suario login 
				usuarioLogin.get().setId(usuario.get().getId());				
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));				
				usuarioLogin.get().setSenha(usuario.get().getSenha());

				
				return usuarioLogin;
				
			}
		}
		
		return Optional.empty();
		
	}
	
	
	private String gerarToken(String usuario, String senha) {

		//gerar codificadao em base 64juncao do usuario com a sneha
		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);
		
		
	}


	// puxando para comparar as senhas
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

	
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senhaDigitada, senhaBanco); //compara as senhas o matches
	
	}


	//este metodo esta puxando para encriptografar a senha. 
	private String criptografarSenha(String senha) {
	
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	}

}
