package com.formacionviewnext.microservicios.app.cursos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionviewnext.microservicios.app.cursos.client.RespuestaFeignClient;
import com.formacionviewnext.microservicios.app.cursos.models.entity.Curso;
import com.formacionviewnext.microservicios.app.cursos.models.repository.CursoRepository;
import com.formacionviewnext.microservicios.commons.services.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso,CursoRepository> implements CursoService {

	@Autowired
	private RespuestaFeignClient respuestaFeignClient;
	
	@Override
	@Transactional(readOnly = true )
	public Curso findCursoByAlumnoId(Long id) {		
		return repository.findCursoByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumnos(Long alumnoId) {
		return respuestaFeignClient.obtenerExamenesIdsConRespuestasAlumnos(alumnoId);
	}

}
