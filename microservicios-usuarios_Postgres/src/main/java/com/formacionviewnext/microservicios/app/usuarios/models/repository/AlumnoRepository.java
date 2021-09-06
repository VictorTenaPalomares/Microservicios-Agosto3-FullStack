package com.formacionviewnext.microservicios.app.usuarios.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;

/*Gracias a esta interfaz nos beneficiamos de todos los métodos CRUD
 * que nos brinda Jpa. Se pueden consultar en el enlace o haciendo ctrl + click
 * en el nombre de crud repository. Además también se pueden implementar
 * nuestros propios métodos en caso de que noi¡ tengamos suficientes*/
/*https://docs.spring.io/spring-data/jpa/docs/2.5.4/reference/html/#repositories*/

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

	// Método custom de acceso a datos que no viene en JPA
	// Remarcar que esto es una consulta propia de JPA (HQL) y que difiere de sql
	@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1% ")
	public List<Alumno> findByNombreOrApellido(String term);
	
	//Consulas personalizada mediante el nombre del método (JPA)	
	public Iterable <Alumno> findAllByOrderByIdAsc();
	public Page <Alumno> findAllByOrderByIdAsc(Pageable pageable);


	
	
	
}
