package com.company.books.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private ICategoriaDao categoriaDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
		
		log.info("inicio metodo buscarCategorias()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			
			response.getCategoriaResponse().setCategoria(categoria);
			
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error al consultar categorias");
			log.error("error al consultar categorias: ", e.getMessage());
			e.getMessage();
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //devuelve error 500
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		
		log.info("inicio metodo buscarPorId()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		List<Categoria> list = new ArrayList<>();
		
		try {
		
			Optional<Categoria> categoria = categoriaDao.findById(id);
			
			if (categoria.isPresent()) {
				list.add(categoria.get());
				response.getCategoriaResponse().setCategoria(list);
				
			} else {
				log.error("Error en consulta categoria");
				response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada");
				
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND); //error 404 
			}
		} catch (Exception e) {
			log.error("Error en consulta categoria");
			response.setMetadata("Respuesta nok", "-1", "Error al consultar categoria");
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500 
		}
		response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria request) {
		log.info("inicio metodo crear()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		List<Categoria> list = new ArrayList<>();
		
		try {
			
			Categoria categoriaGuardada = categoriaDao.save(request);
			
			if (categoriaGuardada != null) {
				list.add(categoriaGuardada);
				response.getCategoriaResponse().setCategoria(list);
			} else {
				log.error("Error en guardar categoria");
				response.setMetadata("Respuesta nok", "-1", "Categoria no guardada");
				
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
			}
			
		} catch (Exception e) {
			log.error("Error en crear categoria");
			response.setMetadata("Respuesta nok", "-1", "Error al crear categoria");
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500 
		}
		
		response.setMetadata("Respuesta ok", "00", "Categoria creada");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
		log.info("inicio metodo actualizar()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		List<Categoria> list = new ArrayList<>();
		
		try {
			
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			
			if (categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());
				
				if (categoriaActualizar != null) {
					response.setMetadata("Respuesta ok", "00", "Categoria actualizada");
					list.add(categoriaActualizar);
					response.getCategoriaResponse().setCategoria(list);
				} else {
					log.error("Error en actualizar categoria");
					response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
					
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST); //error 400
				}
			} else {
				log.error("Error en actualizar categoria");
				response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
				
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND); //error 404
			}
			
		} catch (Exception e) {
			log.error("Error en actualizar categoria", e.getMessage());
			e.getStackTrace();
			
			response.setMetadata("Respuesta nok", "-1", "Error al actualizar la categoria");
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500 
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}
	
	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
		log.info("Inicio metodo eliminar Categorias");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		try {
			// eliminar registro de categoria
			categoriaDao.deleteById(id);
			response.setMetadata("Respuesta OK", "00", "Categoria eliminada");
			
		} catch (Exception e) {
			log.error("Error en eliminacion de categoria", e.getMessage());
			e.getStackTrace();
			
			response.setMetadata("Respuesta nok", "-1", "Error al eliminar la categoria");
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500 
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

}
