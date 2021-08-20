package com.formacionviewnext.microservicios.app.cursos.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formacionviewnext.microservicios.app.cursos.models.entity.Curso;

public interface CursoRepository extends CrudRepository<Curso,Long>{

	// Queremos realizar una consulta que no se contempla en JPARepository
	@Query("select c from Curso c join fetch c.alumnos a where a.id=?1")
	public Curso findCursoByAlumnoId(Long id);
	
	
}
