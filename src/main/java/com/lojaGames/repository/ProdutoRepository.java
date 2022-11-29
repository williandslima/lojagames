package com.lojaGames.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lojaGames.model.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository <ProdutoModel, Long> {
	
	public List <ProdutoModel> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
	public List <ProdutoModel> findByPrecoGreaterThan (@Param("preco") BigDecimal preco);	//buscar preco acima. 
	public List <ProdutoModel> findByPrecoLessThan (@Param("preco") BigDecimal preco); //busca preco abaixo

}
