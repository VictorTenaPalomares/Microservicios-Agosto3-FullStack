package com.formacionviewnext.microservicios.commons.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.formacionviewnext.microservicios.commons.services.CommonService;


public class CommonController<E,S extends CommonService<E>> {

	@Autowired
	protected S service;
	
	@GetMapping // sin especificar la ruta se llama desde la raiz
	public ResponseEntity<?> listar(){ // Retorna un genérico en la respuesta adáptandose al tipo dinámicamente
		 return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/{id}") // Mientras se llamen igual los parámetros no hay que complicarse con atributos
	public ResponseEntity<?> ver (@PathVariable Long id){
		
		 Optional<E> op= service.findById(id);
		 if (op.isEmpty()) {
			 // se retorna la construcción de una respuesta con contenido vacío
			return ResponseEntity.notFound().build(); 
		}
		// retorna el objeto Alumno con el código de estado 200
		return ResponseEntity.ok().body(op.get()); 
	}
	
	@PostMapping // llamada al endpoint raiz del verbo post
	public ResponseEntity<?> crear(@RequestBody E entity){ // Se devuelve un genérico y se recibe un parametro en el cuerpo por eso se anota con @RequestBody
		
		// Convertimos al objeto JSON recibido en el curpo en un Objeto de la clase Alumno
		E entityDb= service.save(entity); 
		
		// Devolvemos el mismo objeto que se acaba de crear con el estado 201
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
		
		
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
