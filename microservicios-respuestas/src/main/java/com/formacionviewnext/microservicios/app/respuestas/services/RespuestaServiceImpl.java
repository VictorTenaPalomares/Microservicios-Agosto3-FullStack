package com.formacionviewnext.microservicios.app.respuestas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionviewnext.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionviewnext.microservicios.app.respuestas.models.repository.RespuestaRespository;

@Service
public class RespuestaServiceImpl implements RespuestaService{

	@Autowired
	private RespuestaRespository repository;
	
	@Override
	@Transactional 
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		return repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdsConRespuestasPorAlumno(Long alumnoId) {
		return repository.findExamenesIdsConRespuestasPorAlumno(alumnoId);
	}

}
