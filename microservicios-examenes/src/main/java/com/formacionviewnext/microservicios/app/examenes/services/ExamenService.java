package com.formacionviewnext.microservicios.app.examenes.services;

import java.util.List;

import com.formacionviewnext.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionviewnext.microservicios.commons.examenes.models.entity.Examen;
import com.formacionviewnext.microservicios.commons.services.CommonService;

public interface ExamenService extends CommonService<Examen>{

	public List<Examen> findExamenByNombre (String term);
	
	public Iterable <Asignatura> findAllAsignaturas();

	public Iterable<Long> findExamenesIdsConRespuestasPorPreguntaIds(Iterable<Long> preguntaIds);
}
