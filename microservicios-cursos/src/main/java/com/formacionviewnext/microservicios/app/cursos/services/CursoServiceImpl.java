package com.formacionviewnext.microservicios.app.cursos.services;

import org.springframework.stereotype.Service;

import com.formacionviewnext.microservicios.app.cursos.models.entity.Curso;
import com.formacionviewnext.microservicios.app.cursos.models.repository.CursoRepository;
import com.formacionviewnext.microservicios.commons.services.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso,CursoRepository> implements CursoService {

}
