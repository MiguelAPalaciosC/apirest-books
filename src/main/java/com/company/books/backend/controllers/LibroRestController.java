package com.company.books.backend.controllers;
import com.company.books.backend.service.ILibroService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/v1")
public class LibroRestController {
	
	@Autowired
	private ILibroService service;

	@GetMapping("/libros")
	@Operation(summary = "Lista todos los libros", description = "Retorna un listado de todos los libro registrados")
	public ResponseEntity<LibroResponseRest> consultarLibros() {
		ResponseEntity<LibroResponseRest> response = service.buscarLibros();
		return response;
	}
	
	@GetMapping("/libros/{id}")
	public ResponseEntity<LibroResponseRest> consultarLibroPorId(@PathVariable Long id) {
		ResponseEntity<LibroResponseRest> response = service.buscarLibroPorId(id);
		return response;
	}
	
	@PostMapping("/libros")
	public ResponseEntity<LibroResponseRest> crearLibro(@RequestBody Libro libro) {
		ResponseEntity<LibroResponseRest> response = service.crearLibro(libro);
		return response;
	}
	
	@PutMapping("/libros/{id}")
	public ResponseEntity<LibroResponseRest> actualizarLibro(@RequestBody Libro libro, @PathVariable Long id) {
		ResponseEntity<LibroResponseRest> response = service.actualizarLibro(libro, id);
		return response;
	}
	
	@DeleteMapping("/libros/{id}")
	public ResponseEntity<LibroResponseRest> eliminarLibro(@PathVariable Long id) {
		ResponseEntity<LibroResponseRest> response = service.eliminarLibro(id);
		return response;
	}
}
