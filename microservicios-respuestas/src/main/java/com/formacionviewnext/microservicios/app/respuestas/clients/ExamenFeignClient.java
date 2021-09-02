package com.formacionviewnext.microservicios.app.respuestas.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.formacionviewnext.microservicios.commons.examenes.models.entity.Examen;

@FeignClient(name="microservicio-examenes")
public interface ExamenFeignClient {
	
	// Vamos a obtener el examen por id
	@GetMapping("/{id}") 
	public Examen obtenerExamenPorId (@PathVariable Long id);
	
	// Este es el endpoint que queremos consumir del microservicio que expone a este cliente http
	@GetMapping("/respondidos-por-preguntas")
	public List<Long> obtenerExamenesIdsPorPreguntasRespondidas(@RequestParam List<Long> preguntaIds); 

}
