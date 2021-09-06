package com.formacionviewnext.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionviewnext.microservicios.app.commons.alumnos.models.entity.Alumno;
import com.formacionviewnext.microservicios.app.cursos.models.entity.Curso;
import com.formacionviewnext.microservicios.app.cursos.models.entity.CursoAlumno;
import com.formacionviewnext.microservicios.app.cursos.services.CursoService;
import com.formacionviewnext.microservicios.commons.controllers.CommonController;
import com.formacionviewnext.microservicios.commons.examenes.models.entity.Examen;
//@CrossOrigin({"*"})// para todos los origenes
@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@Value("${config.balanceador.test}")// para comunicarnos con la variable de entorno del properties
	private String balanceadorTest;
	
	@DeleteMapping("/eliminar-alumno/{id}")
	public  ResponseEntity<?> eliminarCursoAlumnmoPorId(@PathVariable Long id) {
		service.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping // sin especificar la ruta se llama desde la raiz
	@Override
	public ResponseEntity<?> listar(){ // Retorna un genérico en la respuesta adáptandose al tipo dinámicamente
		List<Curso>cursos=((List<Curso>) service.findAll()).stream()
				.map(c->{
					c.getCursoAlumnos()
					.forEach(ca->{
						Alumno alumno=new Alumno();
						alumno.setId(ca.getAlumnoId());
						c.addAlumno(alumno);
					});
					return c; 
				}).collect(Collectors.toList());
		 return ResponseEntity.ok().body(cursos);
	}
	
	@GetMapping("/{id}") // Mientras se llamen igual los parámetros no hay que complicarse con atributos
	@Override
	public ResponseEntity<?> ver (@PathVariable Long id){
		
		 Optional<Curso> op= service.findById(id);
		 if (op.isEmpty()) {
			 // se retorna la construcción de una respuesta con contenido vacío
			return ResponseEntity.notFound().build(); 
		}
		// retorna el objeto Alumno con el código de estado 200
		 Curso curso=op.get();
		 if (curso.getCursoAlumnos().isEmpty()==false) {
			List<Long>ids=curso.getCursoAlumnos().stream().map(ca->{
				return ca.getAlumnoId();
			}).collect(Collectors.toList());
			List<Alumno> alumnos=service.obtenerAlumnosPorCurso(ids);
			curso.setAlumnos(alumnos);
		}		 
		 
		return ResponseEntity.ok().body(curso);
	}
	
	@GetMapping ("/pagina")
	@Override
	public ResponseEntity<?> listar(Pageable pageable){
		 Page<Curso>cursos=service.findAll(pageable).map(c->{
			 c.getCursoAlumnos()
				.forEach(ca->{
					Alumno alumno=new Alumno();
					alumno.setId(ca.getAlumnoId());
					c.addAlumno(alumno);
				});
				return c;
		 });
		 return ResponseEntity.ok().body(cursos);
	}

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
			CursoAlumno cursoAlumno=new CursoAlumno();			
			cursoAlumno.setAlumnoId(unAlumno.getId());
			cursoAlumno.setCurso(dbCurso);
			dbCurso.addCursoAlumnos(cursoAlumno);
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
		CursoAlumno cursoAlumno=new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		// Borramos el alumno del curso a través del custom method de la POJO Alumno
		dbCurso.removeCursoAlumnos(cursoAlumno);

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
			if (examenesId != null && examenesId.size() > 0) {
				List<Examen> examenes = curso.getExamenes().stream().map(e -> {// por cada elemento en el operador map
																				// se emite el examen
					if (examenesId.contains(e.getId())) { // preguntamos si en todos los examenes que han venido
						e.setRespondido(true);
					}
					return e;
				}).collect(Collectors.toList());

				// Como un stream no cambia el estado de origen, tenemos que asignarlo
				// posteriormente
				curso.setExamenes(examenes);
			}
		
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
