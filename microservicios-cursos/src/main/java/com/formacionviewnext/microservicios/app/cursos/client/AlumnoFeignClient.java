package com.formacionviewnext.microservicios.app.cursos.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;

@FeignClient(name="microservicio-usuarios")
public interface AlumnoFeignClient {
	
	@GetMapping("/alumnos-por-curso")
	 public List<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
}
