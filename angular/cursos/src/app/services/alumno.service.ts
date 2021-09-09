import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BASE_ENDPOINT } from '../config/app';
import { Alumno } from '../models/alumno';
import { CommonService } from './common.service';

@Injectable({
  providedIn: 'root'
})
export class AlumnoService extends CommonService<Alumno>{

  protected baseEnpoint = BASE_ENDPOINT + '/alumnos';

  constructor(http: HttpClient) {
    super(http);
  }

  public crearConFoto(alumno: Alumno, archivo: File): Observable<Alumno> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    formData.append('nombre', alumno.nombre);
    formData.append('apellido', alumno.apellido);
    formData.append('email', alumno.email);
    return this.http.post<Alumno>(this.baseEnpoint + '/crear-con-foto',
      formData);
  }

  public editarConFoto(alumno: Alumno, archivo: File): Observable<Alumno> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    formData.append('nombre', alumno.nombre);
    formData.append('apellido', alumno.apellido);
    formData.append('email', alumno.email);
    return this.http.put<Alumno>(`${this.baseEnpoint}/editar-con-foto/${alumno.id}`,
      formData);
  }

  //método que trae la información del backend del método filtrar en microservicio-usuarios
  public filtrarPorNombre(nombre:string):Observable<Alumno[]>{
    return this.http.get<Alumno[]>(`${this.baseEnpoint}/filtrar/${nombre}`); // el endpoint al que vamos a buscar la información
  }


}