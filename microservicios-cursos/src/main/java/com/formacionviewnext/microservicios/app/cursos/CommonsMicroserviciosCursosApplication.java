package com.formacionviewnext.microservicios.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({ "com.formacionviewnext.microservicios.app.commons.alumnos.models.entity",
		"com.formacionviewnext.microservicios.commons.examenes.models.entity",
		"com.formacionviewnext.microservicios.app.cursos.models.entity" })
public class CommonsMicroserviciosCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonsMicroserviciosCursosApplication.class, args);
	}

}
