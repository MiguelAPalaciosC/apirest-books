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

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImpl implements ILibroService{

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

	@Autowired
	private ILibroDao libroDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibros() {
		log.info("inicio del metodo buscarLibro()");
		
		LibroResponseRest response = new LibroResponseRest();
		try {
			List<Libro> libro = (List<Libro>) libroDao.findAll();
			
			response.getLibroResponse().setLibros(libro);
			
			response.setMetadata("Respuesta Ok", "00", "Respuesta exitosa");
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error en la consulta de libros");
			log.error("Error en la consulta de libros: ", e.getMessage());
			e.getMessage();
			
			return new ResponseEntity<LibroResponseRest> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<LibroResponseRest> (response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibroPorId(Long id) {
		log.info("inicio del metodo buscar libro por id");
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> list = new ArrayList<>();
		
		try {
			Optional<Libro> libro = libroDao.findById(id);
			if (libro.isPresent()) {
				list.add(libro.get());
				response.getLibroResponse().setLibros(list);
				
				response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
			} else {
				response.setMetadata("Respuesta nok", "-1", "Libro no encontrado");
				log.error("Error al buscar libro");
				
				return new ResponseEntity<LibroResponseRest> (response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Error en la consulta");
			log.error("Error al buscar libro: ", e.getMessage());
			e.getMessage();
			
			return new ResponseEntity<LibroResponseRest> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<LibroResponseRest> (response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> crearLibro(Libro request) {
		log.info("Ingreso al metodo crear libro");
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> libro = new ArrayList<>();
		try {
			Libro libroGuardado = libroDao.save(request);
			
			if (libroGuardado != null) {
				libro.add(libroGuardado);
				
				response.getLibroResponse().setLibros(libro);
				
				response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
			} else {
				response.setMetadata("Respuesta nok", "-1", "Libro no guardado");
				log.error("Error al guardar libro");
				
				return new ResponseEntity<LibroResponseRest> (response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta nok", "-1", "Libro no guardado");
			log.error("Error al guardar libro", e.getMessage());
			e.getMessage();
			return new ResponseEntity<LibroResponseRest> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<LibroResponseRest> (response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> actualizarLibro(Libro request, Long id) {
		log.info("Ingreso metodo actulizar libro");
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> libro = new ArrayList<>();
		
		try {
			Optional<Libro> libroBuscado = libroDao.findById(id);
			
			if (libroBuscado.isPresent()) {
				libroBuscado.get().setDescripcion(request.getDescripcion());
				libroBuscado.get().setNombre(request.getNombre());
				libroBuscado.get().setCategoria(request.getCategoria());
				
				Libro libroActualizado = libroDao.save(libroBuscado.get()); //actualizando
				
				if (libroActualizado != null) {
					response.setMetadata("Respuesta ok", "00", "Libro actualizado exitosamente");
					libro.add(libroActualizado);
					response.getLibroResponse().setLibros(libro);
				} else {
					log.error("Error al actualizar libro");
					response.setMetadata("Respuesta nok", "-1", "Libro no actualizado");
					
					return new ResponseEntity<LibroResponseRest> (response, HttpStatus.BAD_REQUEST);
				}
			} else {
				log.error("Error al actualizar libro");
				response.setMetadata("Respuesta nok", "-1", "Libro no actualizado");
				
				return new ResponseEntity<LibroResponseRest> (response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Error al actualizar libro: ", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Libro no actualizado");
			
			return new ResponseEntity<LibroResponseRest> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<LibroResponseRest> (response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> eliminarLibro(Long id) {
		log.info("Ingreso al metodo eliminar libro");
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			libroDao.deleteById(id);
				
			response.setMetadata("Respuesta ok", "00", "Libro eliminado exitosamente");	
		} catch (Exception e) {
			log.error("Error al eliminar libro: ", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Libro no eliminado");
			return new ResponseEntity<LibroResponseRest> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<LibroResponseRest> (response, HttpStatus.OK);
	}

}
