package com.formacionviewnext.microservicios.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
	
	@GetMapping ("/pagina")
	public ResponseEntity<?> listar(Pageable pageable){
		 return ResponseEntity.ok().body(service.findAll(pageable));
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
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result){ // Se devuelve un genérico y se recibe un parametro en el cuerpo por eso se anota con @RequestBody
		
		//validamos antes de guardar, retornamos llamada al método
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
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
	
	// protected para que lo podamos utilizar en los otros controladores a través de la herencia
	protected ResponseEntity<?> validar(BindingResult result){
		// Como recibimos un Json, lo implementamos con un map de Java(clave/valor)
		Map<String, Object>errores=new HashMap<String, Object>();
		
		// El parámetro result nos viene como un iterable, por lo que lo recorremos
		// con un for y lo guardamos en el mapa
		result.getFieldErrors().forEach(err->{
			errores.put(err.getField(),"Hay un error en el campo "+ err.getField()+" de tipo "+ err.getDefaultMessage());
		});
		
		// Ya tenemos el mapa, ahora lo metemos en la response entity del retorno
		// dándole un 400 de código de respuesta http (en el cuerpo de la respuesta va el map Json)
		return ResponseEntity.badRequest().body(errores);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
