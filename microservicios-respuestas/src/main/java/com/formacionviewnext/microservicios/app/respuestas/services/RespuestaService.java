package com.formacionviewnext.microservicios.app.respuestas.services;

import com.formacionviewnext.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {
	// guarda y retorna las respuestasen Json ya persistidas junto a su id
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId,Long examenId);

	public Iterable<Long> findExamenesIdsConRespuestasPorAlumno(Long alumnoId);

	
}
