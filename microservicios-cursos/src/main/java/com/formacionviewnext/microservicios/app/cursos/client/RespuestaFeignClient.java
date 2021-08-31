package com.formacionviewnext.microservicios.app.cursos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="microservicio-respuestas")
public interface RespuestaFeignClient {
	// métodos sin cuerpo únicamente para establecer los recursos que vamos a 
	//consumir como clientes al microservicio especificado en el endpoint
	//también hay que ewspecificar el tipo de retorno, es decir , realizar un contrato
	// de lo que posteriormente haremos con esos datos recibidos
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumnos(@PathVariable Long alumnoId);
	
	

}
