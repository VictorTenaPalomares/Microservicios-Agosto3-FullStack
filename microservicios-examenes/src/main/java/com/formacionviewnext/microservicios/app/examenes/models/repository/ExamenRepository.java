package com.formacionviewnext.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionviewnext.microservicios.commons.examenes.models.entity.Examen;

public interface ExamenRepository extends PagingAndSortingRepository<Examen,Long> {
	
	@Query("select e from Examen e where e.name like %?1%")
	public List<Examen> findExamenByNombre (String term);

}
