package com.formacionviewnext.microservicios.app.examenes.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formacionviewnext.microservicios.app.examenes.services.ExamenService;
import com.formacionviewnext.microservicios.commons.controllers.CommonController;
import com.formacionviewnext.microservicios.commons.examenes.models.entity.Examen;
import com.formacionviewnext.microservicios.commons.examenes.models.entity.Pregunta;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

	@GetMapping("/respondidos-por-preguntas")
	public ResponseEntity<?> obtenerExamenesIdsPorPreguntasRespondidas(@RequestParam List<Long> preguntaIds) {
		return ResponseEntity.ok(service.findExamenesIdsConRespuestasPorPreguntaIds(preguntaIds));
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Examen examen,BindingResult result, @PathVariable Long id) {
		
		//validamos antes de guardar, retornamos llamada al método
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Examen> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Examen examenDb = o.get();
		examenDb.setName(examen.getName());

		// Para las preguntas eliminadas
		

		// Vamos a iterar sobre las preguntas del examen, en caso de que existan en la
		// bd
		// perfecto, pero si no existen es que en el examen que estamos editando
		// se han borrado y las eliminamos de la bd para que no existan.
		
		List<Pregunta>eliminadas=examenDb.getPreguntas()//1. Obtenemos las preguntas
		.stream() // 2. Como es una lista, convertimos a un stream de Java8
		.filter(pdb -> !examen.getPreguntas().contains(pdb))
		.collect(Collectors.toList());//3.Filtramos considerando que se creará un unevo stream con la lista de preguntas que NO existen en la base de datos pero sí en la lista de reciente creación
		
		
		
		eliminadas.forEach(examenDb::removePregunta); // 4 Con la lista de las que no existen pasamos por cada una y la borramos

		examenDb.setPreguntas(examen.getPreguntas());
		examenDb.setAsignaturaHija(examen.getAsignaturaHija());
		examenDb.setAsignaturaPadre(examen.getAsignaturaPadre());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
		// return ResponseEntity.ok(service.save(examen));
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?>filtrar(@PathVariable String term) {
		return ResponseEntity.ok(service.findExamenByNombre(term));
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
