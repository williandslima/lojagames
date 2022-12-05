package com.lojaGames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lojaGames.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByUsuario(String usuario);
	//public Optional<Usuario> findByIdUsuario(Long id);

	public List <Usuario> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

	
}