package com.formacionviewnext.microservicios.app.usuarios.services;

import java.util.Optional;

import com.formacionviewnext.microservicios.app.usuarios.models.entity.Alumno;

/*Contrato que accede a datos que contendrá la lógica de negocio*/

public interface AlumnoService {

	// Un iterable de alumno, podria ser un List pero Iterable es más genérico
	public Iterable<Alumno> findAll();
	
	// Nos ayudará a saber si existe un determinado alumno y manejará null eficazmente (Java8)
	public Optional<Alumno> findById(Long id);
	
	// Guarda y retorna el alumno que recibe por parámetro en formato Json
	public Alumno save(Alumno alumno);  
	
	// Borrar , no devuelve na
	public void deleteById(Long id);
	
	
}
