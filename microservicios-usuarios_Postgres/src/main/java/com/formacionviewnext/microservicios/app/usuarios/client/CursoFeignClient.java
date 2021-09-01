package com.formacionviewnext.microservicios.app.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="microservicio-cursos")
public interface CursoFeignClient {

    // Estamos convirtiendo a usuarios en cliente http de cursos
	// nos comunicamos con este método que existe en su controlador
	// apuntando al mismo endpoint
	
	@DeleteMapping("/eliminar-alumno/{id}")
	public void eliminarCursoAlumnoPorId(@PathVariable Long id);
	
}
