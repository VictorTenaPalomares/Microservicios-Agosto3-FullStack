package com.formacionviewnext.microservicios.app.usuarios.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionviewnext.microservicios.app.usuarios.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.usuarios.models.repository.AlumnoRepository;


@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	// Traemos a esta lógica de negocio todo lo referente a los datos del modelo
	@Autowired
	private AlumnoRepository repository; 

	@Override
	@Transactional(readOnly=true) // como es una select atributo read only
	public Iterable<Alumno> findAll() {
		// podemos usar el repository con los métodos que nos da JPA
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly=true) // como es una select atributo read only
	public Optional<Alumno> findById(Long id) {
		// podemos usar el repository con los métodos que nos da JPA
		return repository.findById(id);
	}

	@Override
	@Transactional // aqui sin atributo porque lee y modifica
	public Alumno save(Alumno alumno) {
		// podemos usar el repository con los métodos que nos da JPA
		return repository.save(alumno);
	}

	@Override
	@Transactional // aqui sin atributo porque lee y modifica
	public void deleteById(Long id) {
		// podemos usar el repository con los métodos que nos da JPA
		repository.deleteById(id);

	}

}
