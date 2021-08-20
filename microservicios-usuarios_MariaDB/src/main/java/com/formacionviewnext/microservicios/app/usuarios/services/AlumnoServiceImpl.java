package com.formacionviewnext.microservicios.app.usuarios.services;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.formacionviewnext.microservicios.commons.services.CommonServiceImpl;


@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

	// Implementación de la query que ha sido escrita en el repository
	@Override
	@Transactional(readOnly = true)	
	public List<Alumno> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}
	
	

}
