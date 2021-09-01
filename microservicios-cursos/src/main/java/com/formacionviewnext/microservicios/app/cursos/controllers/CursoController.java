package com.formacionviewnext.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.cursos.models.entity.Curso;
import com.formacionviewnext.microservicios.app.cursos.services.CursoService;
import com.formacionviewnext.microservicios.commons.controllers.CommonController;
import com.formacionviewnext.microservicios.commons.examenes.models.entity.Examen;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@Value("${config.balanceador.test}")// para comunicarnos con la variable de entorno del properties
	private String balanceadorTest;
	
	
	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
	Map<String,Object>response=new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("cursos", service.findAll());
		
		
	  return ResponseEntity.ok(response);
	}

	// En este método editamos un determinado curso
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso,BindingResult result, @PathVariable Long id) {
		
		//validamos antes de guardar, retornamos llamada al método
				if(result.hasErrors()) {
					return this.validar(result);
				}
		
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
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id){
		
		Curso curso=service.findCursoByAlumnoId(id);
		//validamos que el curso no venga vacío
		if (curso!=null) {
			//Almacenamos todos los Ids de los examenes 
			List<Long>examenesId=(List<Long>) service.obtenerExamenesIdsConRespuestasAlumnos(id);
			// Mapeamos a otra lista convirtiéndolos a true a través de Java 8 en los examens que si han sido respondidios
			List<Examen>examenes=curso.getExamenes()
					.stream()
					.map(e->{//por cada elemento en el operador map se emite el examen
						if(examenesId.contains(e.getId())){ //preguntamos si en todos los examenes que han venido
							e.setRespondido(true);
						}
						return e;
					}).collect(Collectors.toList());
			
			// Como un stream no cambia el estado de origen, tenemos que asignarlo posteriormente
			curso.setExamenes(examenes);
		
		
		}		
		
	
		return ResponseEntity.ok(curso); 	
	}
	
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {

		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();

		examenes.forEach(e -> {
			dbCurso.addExamen(e);
		});

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
	}

	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {

		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();

		dbCurso.removeExamen(examen);

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbCurso));
	}
	
	
	
	
	
	
}
