package com.formacionviewnext.microservicios.app.usuarios.services;

import java.util.List;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.commons.services.CommonService;

/*Contrato que accede a datos que contendrá la lógica de negocio*/

public interface AlumnoService extends CommonService<Alumno>{

	public List<Alumno> findByNombreOrApellido(String term);
	
	public Iterable<Alumno> findAllById(Iterable<Long> ids);
	
	public void eliminarCursoAlumnoPorId(Long id);
	
	
	
	
}
