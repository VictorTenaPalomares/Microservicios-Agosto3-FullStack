import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BASE_ENDPOINT } from '../config/app';
import { Alumno } from '../models/alumno';
import { Curso } from '../models/curso';
import { CommonService } from './common.service';

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso>{

  protected baseEnpoint = BASE_ENDPOINT + '/cursos';

  constructor(http: HttpClient) { // sobreescritura del método constructor que está en la clase padre
    super(http);
  }

  asignarAlumnos(curso: Curso, alumnos: Alumno[]): Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEnpoint}/${curso.id}/asignar-alumnos`, alumnos,
      { headers: this.cabeceras })


  }

  eliminarAlumno(curso: Curso, alumno: Alumno): Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEnpoint}/${curso.id}/eliminar-alumno`,
      alumno,
      { headers: this.cabeceras }); 
  }



}