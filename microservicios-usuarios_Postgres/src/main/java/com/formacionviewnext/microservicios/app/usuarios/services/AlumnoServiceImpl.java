package com.formacionviewnext.microservicios.app.usuarios.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.usuarios.client.CursoFeignClient;
import com.formacionviewnext.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.formacionviewnext.microservicios.commons.services.CommonServiceImpl;


@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

	//Inyectamos la interfaz cliente que hemos creado con feign
	@Autowired
	private CursoFeignClient cursoFeignClient;	
	
	
	// Implementación de la query que ha sido escrita en el repository
	@Override
	@Transactional(readOnly = true)	
	public List<Alumno> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}

	//Implementamos el método que efectúa el borrado del alumno en MariaDb
	@Override
	public void eliminarCursoAlumnoPorId(Long id) {
		cursoFeignClient.eliminarCursoAlumnoPorId(id);		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.eliminarCursoAlumnoPorId(id);
	}

	@Override
	@Transactional(readOnly = true)	
	public Iterable<Alumno> findAll() {
		return repository.findAllByOrderByIdAsc();
	}

	@Override
	@Transactional(readOnly = true)	
	public Page<Alumno> findAll(Pageable pageable) {
		return repository.findAllByOrderByIdAsc(pageable);
	}
	
	
	
	
	
	
	

}
