package com.formacionviewnext.microservicios.app.respuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionviewnext.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionviewnext.microservicios.app.respuestas.models.repository.RespuestaRespository;

@Service
public class RespuestaServiceImpl implements RespuestaService{

	
	//@Autowired
	//private ExamenFeignClient examenFeignClient;
	
	@Autowired
	private RespuestaRespository repository;
	
	@Override
	
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}

	@Override
	
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		/*Examen examen=examenFeignClient.obtenerExamenPorId(examenId);
		List<Pregunta>preguntas=examen.getPreguntas();
		List<Long>preguntaIds=preguntas.stream().map(p->
			p.getId()).collect(Collectors.toList());
		List<Respuesta>respuestas=(List<Respuesta>) repository
				.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntaIds);
		respuestas=respuestas.stream().map(r->{
		preguntas.forEach(p->{
			if (p.getId()==r.getPreguntaId()) {
				r.setPregunta(p);
			}
		});		
		return r;
		}).collect(Collectors.toList());*/		
		List<Respuesta>respuestas=(List<Respuesta>) repository
				.findRespuestaByAlumnoByExamen(alumnoId,examenId);
		
		return respuestas;
	}
	
	

	@Override	
	public Iterable<Long> findExamenesIdsConRespuestasPorAlumno(Long alumnoId) {
		/*List<Respuesta>respuestasAlumno=(List<Respuesta>) repository.findByAlumnoId(alumnoId);
		List <Long> examenIds=Collections.emptyList();
		if (respuestasAlumno.size() > 0) {
			List<Long> preguntaIds = respuestasAlumno
					.stream()
					.map(r -> r.getPreguntaId())
					.collect(Collectors.toList());		
			examenIds=examenFeignClient.obtenerExamenesIdsPorPreguntasRespondidas(preguntaIds);
		}*/
		
		List<Respuesta>respuestasAlumno=(List<Respuesta>) repository.findExamenesIdsConRespuestasPorAlumno(alumnoId);
		List <Long> examenIds=respuestasAlumno
				.stream()
				.map(r->r.getPregunta().getExamen().getId())
				.distinct()
				.collect(Collectors.toList());						
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		return repository.findByAlumnoId(alumnoId);
	}
	
	

}
