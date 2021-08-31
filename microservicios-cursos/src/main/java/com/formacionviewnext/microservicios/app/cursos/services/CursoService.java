package com.formacionviewnext.microservicios.app.cursos.services;

import com.formacionviewnext.microservicios.app.cursos.models.entity.Curso;
import com.formacionviewnext.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {

	
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumnos( Long alumnoId);
	
	public Curso findCursoByAlumnoId(Long id);
}
