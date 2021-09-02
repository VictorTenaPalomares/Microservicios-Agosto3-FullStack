package com.formacionviewnext.microservicios.app.examenes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionviewnext.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.formacionviewnext.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacionviewnext.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionviewnext.microservicios.commons.examenes.models.entity.Examen;
import com.formacionviewnext.microservicios.commons.services.CommonServiceImpl;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService {

	@Autowired
	private AsignaturaRepository asignaturaRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<Examen> findExamenByNombre(String term) {
		return repository.findExamenByNombre(term);
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Asignatura> findAllAsignaturas() {
		return  asignaturaRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Long> findExamenesIdsConRespuestasPorPreguntaIds(Iterable<Long> preguntaIds) {
		return repository.findExamenesIdsConRespuestasPorPreguntaIds(preguntaIds);
	}

}
