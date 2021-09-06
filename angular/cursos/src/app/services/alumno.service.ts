import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alumno } from '../models/alumno';

@Injectable({
  providedIn: 'root'
})
export class AlumnoService {

  private baseEnpoint = 'http://localhost:8090/api/alumnos';
  private cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  // la inyeccion se efectua mediante constructor luego lo usamos en cada uno de los métodos 
  constructor(private http: HttpClient) { }

  public listar(): Observable<Alumno[]> {
    return this.http.get<Alumno[]>(this.baseEnpoint);
  }

  public listarPaginas(page: string, size: string): Observable<any> { // devuelve un tipo genérico
    const params = new HttpParams() // buena práctica http params siempre como constante
      .set('page', page)// se invoca en base a la misma instancia de forma encadenada para evitar crear una instancia nueva cada vez. por que httpParam es inmutable
      .set('size', size);
    return this.http.get<any>(`${this.baseEnpoint}/pagina`, { params: params });
  }

  public ver(id: number): Observable<Alumno> {
    //return this.http.get<Alumno>( this.baseEnpoint + '/id' ); // forma más cercana a Java
    return this.http.get<Alumno>(`${this.baseEnpoint}/${id}`); // con template strings
  }

  public crear(alumno: Alumno): Observable<Alumno> { // recibe un objeto de tipo alumno y retorna Un observable (flujo reactivo) de tipo Alumno
    return this.http.post<Alumno>(this.baseEnpoint, alumno, { headers: this.cabeceras });
  }

  public editar(alumno: Alumno): Observable<Alumno> {
    return this.http.put<Alumno>(`${this.baseEnpoint}/${alumno.id}`, alumno,
      { headers: this.cabeceras });
  }

  public eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseEnpoint}/${id}`);
  }
}
