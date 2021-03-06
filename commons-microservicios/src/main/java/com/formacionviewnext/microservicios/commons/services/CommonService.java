package com.formacionviewnext.microservicios.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/*Contrato que accede a datos que contendrá la lógica de negocio*/

public interface CommonService<E> {

	// Un iterable de alumno, podria ser un List pero Iterable es más genérico
	public Iterable<E> findAll();
	
	//Método añadido a posteriori para añadir la paginación
	public Page<E> findAll(Pageable pageable);
	
	// Nos ayudará a saber si existe un determinado alumno y manejará null eficazmente (Java8)
	public Optional<E> findById(Long id);
	
	// Guarda y retorna el alumno que recibe por parámetro en formato Json
	public E save(E entity);  
	
	// Borrar , no devuelve na
	public void deleteById(Long id);
	
	
}
