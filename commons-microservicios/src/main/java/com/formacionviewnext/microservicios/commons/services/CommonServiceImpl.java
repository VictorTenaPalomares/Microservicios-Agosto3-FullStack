package com.formacionviewnext.microservicios.commons.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;



public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E,Long>> implements CommonService<E> {
	
	// Traemos a esta lógica de negocio todo lo referente a los datos del modelo
	@Autowired
	protected R repository; 

	@Override
	@Transactional(readOnly=true) // como es una select atributo read only
	public Iterable<E> findAll() {
		// podemos usar el repository con los métodos que nos da JPA
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly=true) // como es una select atributo read only
	public Optional<E> findById(Long id) {
		// podemos usar el repository con los métodos que nos da JPA
		return repository.findById(id);
	}

	@Override
	@Transactional // aqui sin atributo porque lee y modifica
	public E save(E entity) {
		// podemos usar el repository con los métodos que nos da JPA
		return repository.save(entity);
	}

	@Override
	@Transactional // aqui sin atributo porque lee y modifica
	public void deleteById(Long id) {
		// podemos usar el repository con los métodos que nos da JPA
		repository.deleteById(id);

	}

	@Override
	@Transactional(readOnly=true)
	public Page<E> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
