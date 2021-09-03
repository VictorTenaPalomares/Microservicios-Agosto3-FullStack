import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlumnoService {

private baseEnpoint= 'http://localhost:8090/api/alumnos';

  // la inyeccion se efectua mediante constructor
  constructor(private http: HttpClient) { }
}
