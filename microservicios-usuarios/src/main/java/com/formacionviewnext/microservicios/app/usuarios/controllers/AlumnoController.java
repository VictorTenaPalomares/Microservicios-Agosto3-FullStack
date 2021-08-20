package com.formacionviewnext.microservicios.app.usuarios.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionviewnext.microservicios.app.usuarios.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.usuarios.services.AlumnoService;

@RestController
public class AlumnoController {

	@Autowired
	private AlumnoService service;
	
	@GetMapping // sin especificar la ruta se llama desde la raiz
	public ResponseEntity<?> listar(){ // Retorna un genérico en la respuesta adáptandose al tipo dinámicamente
		 return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/{id}") // Mientras se llamen igual los parámetros no hay que complicarse con atributos
	public ResponseEntity<?> ver (@PathVariable Long id){
		
		 Optional<Alumno> op= service.findById(id);
		 if (op.isEmpty()) {
			 // se retorna la construcción de una respuesta con contenido vacío
			return ResponseEntity.notFound().build(); 
		}
		// retorna el objeto Alumno con el código de estado 200
		return ResponseEntity.ok().body(op.get()); 
	}
	
	@PostMapping // llamada al endpoint raiz del verbo post
	public ResponseEntity<?> crear(@RequestBody Alumno alumno){ // Se devuelve un genérico y se recibe un parametro en el cuerpo por eso se anota con @RequestBody
		
		// Convertimos al objeto JSON recibido en el curpo en un Objeto de la clase Alumno
		Alumno alumnoDb= service.save(alumno); 
		
		// Devolvemos el mismo objeto que se acaba de crear con el estado 201
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDb);
		
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Alumno alumno,@PathVariable Long id){
		
		// Primero buscamos el objeto a modificar en la base de datos
		Optional<Alumno> op= service.findById(id);
		
		// Validamos que exista
		 if (op.isEmpty()) {
			 // se retorna la construcción de una respuesta con contenido vacío
			return ResponseEntity.notFound().build(); 
		}
		
		// Si estmos aqui es que sí existe, por lo tanto lo obtenemos con el get de Optional
		Alumno alumnoDb=op.get(); 
		
		// Modificamos lo que nos interesa
		alumnoDb.setNombre(alumnoDb.getNombre());
		alumnoDb.setApellido(alumnoDb.getApellido());
		alumnoDb.setEmail(alumnoDb.getEmail());
		
		// Devolvemos el mismo objeto que se acaba de crear con el estado 201
		// CUIDADO, antes de devolverlo hay que persistirlo :service.save(alumnoDb)
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
