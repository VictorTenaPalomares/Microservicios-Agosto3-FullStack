package com.formacionviewnext.microservicios.app.usuarios.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.usuarios.services.AlumnoService;
import com.formacionviewnext.microservicios.commons.controllers.CommonController;
//@CrossOrigin({"*"})// para todos los origenes
@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

	 @GetMapping("/alumnos-por-curso")
	 public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
		 return ResponseEntity.ok(service.findAllById(ids));
	 }
	 
	
	
	//Método encargado de mostrar la imagen
	@GetMapping("uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id){
		
		//obtenemos el alumno y validamos que no venga vacío, también la foto		
		Optional<Alumno> op= service.findById(id);
		
		 if (op.isEmpty()||op.get().getFoto()==null) {
			return ResponseEntity.notFound().build(); 
		}
		 
		 //guradamos la foto como recurso de Spring
		 Resource imagen=new ByteArrayResource(op.get().getFoto());
		 
		 return  ResponseEntity.ok()
				 .contentType(MediaType.IMAGE_JPEG)
				 .body(imagen);
		 
	}
	
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
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());
		
		// Devolvemos el mismo objeto que se acaba de crear con el estado 201
		// CUIDADO, antes de devolverlo hay que persistirlo :service.save(alumnoDb)
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
	}
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid Alumno alumno,BindingResult result,@PathVariable Long id,
			@RequestParam MultipartFile archivo) throws IOException{
		
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
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());
		
		if (!archivo.isEmpty()) {
			alumnoDb.setFoto(archivo.getBytes());
		}
		
		// Devolvemos el mismo objeto que se acaba de crear con el estado 201
		// CUIDADO, antes de devolverlo hay que persistirlo :service.save(alumnoDb)
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){	
		// Recorder que el service es protected y lo estamos heredando
		return ResponseEntity.ok(service.findByNombreOrApellido(term));	
	}
	
	// Para la foto sobreescribimos el método crear que nos viene en la herencia,
	// aunque como le cambiamos el nombre, no es una sobreescritura, pero seguimos llamando
	// al método de la clase padre cuando le pasamos los bytes de la foto para que complete la operación
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result,
			@RequestParam MultipartFile archivo) throws IOException {
	
		// validamos, si no es vacío, se lo asignamos al alumno a través del método que lo convierte en bytes
		if (!archivo.isEmpty()) {
			alumno.setFoto(archivo.getBytes());
		}
		return super.crear(alumno, result);
	
	}	
	
	
	
	
	
}
