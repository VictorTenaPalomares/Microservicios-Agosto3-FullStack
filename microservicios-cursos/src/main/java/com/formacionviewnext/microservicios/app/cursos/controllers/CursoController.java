package com.formacionviewnext.microservicios.app.cursos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.cursos.models.entity.Curso;
import com.formacionviewnext.microservicios.app.cursos.services.CursoService;
import com.formacionviewnext.microservicios.commons.controllers.CommonController;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	// En este método editamos un determinado curso
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = o.get();
		dbCurso.setNombre(curso.getNombre());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));

	}

	// En este método metemos una lista de alumnos al curso
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {

		// Ir a buscar el curso a través del id
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		// Obtener el curso db
		Curso dbCurso = o.get();

		// Recorremos la lista de alumnos que se ha recibido por el cuerpo de la
		// petición, por cada alumno, usamos el método custom addAlumno que se ha
		// implementado en la clase POJO Curso.
		alumnos.forEach(unAlumno -> {
			dbCurso.addAlumno(unAlumno);
		});

		// Guardamos y retornamos
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
	}

	// En este método eliminamos un solo alumno del curso por su id
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {

		// Ir a buscar el curso a través del id
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		// Obtener el curso db
		Curso dbCurso = o.get();

		// Borramos el alumno del curso a través del custom method de la POJO Alumno
		dbCurso.removeAlumno(alumno);

		// Guardamos y retornamos
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
	}
}
