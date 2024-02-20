package com.company.books.backend.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public CategoriaResponseRest buscarCategorias() {
		
		log.info("inicio metodo buscarCategorias()");
		
		CategoriaResponseRest respose = new CategoriaResponseRest();
		
		try {
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			
			respose.getCategoriaResponse().setCategoria(categoria);
			
			respose.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
		} catch (Exception e) {
			respose.setMetadata("Respuesta nok", "-1", "Respuesta incorrecta");
			log.error("error al consultar categorias: ", e.getMessage());
			e.getMessage();
		}
		
		return respose;
	}
	

}
