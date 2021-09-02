package com.formacionviewnext.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionviewnext.microservicios.commons.examenes.models.entity.Examen;

public interface ExamenRepository extends PagingAndSortingRepository<Examen,Long> {
	
	@Query("select e from Examen e where e.name like %?1%")
	public List<Examen> findExamenByNombre (String term);

	@Query("select e.id from Pregunta p join p.examen e where p.id in ?1 group by e.id")
	public Iterable<Long> findExamenesIdsConRespuestasPorPreguntaIds(Iterable<Long> preguntaIds);
	
}
