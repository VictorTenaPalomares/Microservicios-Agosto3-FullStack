package com.formacionviewnext.microservicios.app.usuarios.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.usuarios.services.AlumnoService;
import com.formacionviewnext.microservicios.commons.controllers.CommonController;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno,BindingResult result,@PathVariable Long id){
		
		//validamos antes de guardar, retornamos llamada al método
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
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
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
	
		// Recorder que el service es protected y lo estamos heredando
		return ResponseEntity.ok(service.findByNombreOrApellido(term));	
	}	
	
	
	
	
}
