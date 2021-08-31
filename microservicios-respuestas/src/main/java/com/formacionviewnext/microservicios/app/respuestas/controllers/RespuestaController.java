package com.formacionviewnext.microservicios.app.respuestas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionviewnext.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionviewnext.microservicios.app.respuestas.services.RespuestaService;

@RestController
public class RespuestaController {

	// inyectamos el autowirde del service porque lo vamos a usar para guardar las
	// respuestas
	@Autowired
	private RespuestaService service;

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas) {
		// persitimos y guradamos en variable
		Iterable<Respuesta> respuestasDb = service.saveAll(respuestas);
		// retornamos las respuestas ya persistidas
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
	}

	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?> obtenerRespuestasPorAlumnoPorExamen(@PathVariable Long alumnoId,
			@PathVariable Long examenId) {
		Iterable<Respuesta> respuestas = service.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return ResponseEntity.ok(respuestas);
	}
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumnos(@PathVariable Long alumnoId){
		 Iterable <Long> examenesIds=service.findExamenesIdsConRespuestasPorAlumno(alumnoId);
		 return ResponseEntity.ok(examenesIds);
	}
}