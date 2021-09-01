package com.formacionviewnext.microservicios.app.cursos.services;

import java.util.List;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.cursos.models.entity.Curso;
import com.formacionviewnext.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {

	
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumnos( Long alumnoId);
	
	public Curso findCursoByAlumnoId(Long id);
	
	public List<Alumno> obtenerAlumnosPorCurso(List<Long> ids);
	
	public void eliminarCursoAlumnoPorId(Long alumnoId);
}
