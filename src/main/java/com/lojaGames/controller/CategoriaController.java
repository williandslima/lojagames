package com.lojaGames.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.lojaGames.model.CategoriaModel;
import com.lojaGames.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<CategoriaModel>> getAll() {

		return ResponseEntity.ok(categoriaRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaModel> getById(@PathVariable Long id) {

		return categoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping("/categoria/{tipo}")
	public ResponseEntity<List<CategoriaModel>> getByTipo(@PathVariable String tipo) {

		return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));

	}

	// postar
	@PostMapping
	public ResponseEntity<CategoriaModel> postCategoria(@Valid @RequestBody CategoriaModel categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));

	}

	// atualizar
	@PutMapping
	public ResponseEntity<CategoriaModel> putCategoria(@Valid @RequestBody CategoriaModel categoria) {

		return categoriaRepository.findById(categoria.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria)))
				.orElse(ResponseEntity.notFound().build());

	}

	// deletar
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteTema(@PathVariable Long id) {

		Optional<CategoriaModel> recebeidTema = categoriaRepository.findById(id);

		if (recebeidTema.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		categoriaRepository.deleteById(id);

	}

}
