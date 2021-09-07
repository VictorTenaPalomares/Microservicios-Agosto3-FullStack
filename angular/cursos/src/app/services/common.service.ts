import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Generic } from '../models/generic';


export abstract class CommonService <E extends Generic>{

  protected baseEnpoint :string;
  
  protected cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  // la inyeccion se efectua mediante constructor luego lo usamos en cada uno de los métodos 
  constructor(protected http: HttpClient) { }

  public listar(): Observable<E[]> {
    return this.http.get<E[]>(this.baseEnpoint);
  }

  public listarPaginas(page: string, size: string): Observable<any> { // devuelve un tipo genérico
    const params = new HttpParams() // buena práctica http params siempre como constante
      .set('page', page)// se invoca en base a la misma instancia de forma encadenada para evitar crear una instancia nueva cada vez. por que httpParam es inmutable
      .set('size', size);
    return this.http.get<any>(`${this.baseEnpoint}/pagina`, { params: params });
  }

  public ver(id: number): Observable<E> {
    //return this.http.get<E>( this.baseEnpoint + '/id' ); // forma más cercana a Java
    return this.http.get<E>(`${this.baseEnpoint}/${id}`); // con template strings
  }

  public crear(e: E): Observable<E> { // recibe un objeto de tipo alumno y retorna Un observable (flujo reactivo) de tipo Alumno
    return this.http.post<E>(this.baseEnpoint, e, { headers: this.cabeceras });
  }

  public editar(e: E): Observable<E> {
    return this.http.put<E>(`${this.baseEnpoint}/${e.id}`, e,
      { headers: this.cabeceras });
  }

  public eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseEnpoint}/${id}`);
  }
}
