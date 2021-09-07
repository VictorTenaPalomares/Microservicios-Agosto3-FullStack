import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BASE_ENDPOINT } from '../config/app';
import { Curso } from '../models/curso';
import { CommonService } from './common.service';

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso>{

  protected baseEnpoint = BASE_ENDPOINT + '/cursos';

  constructor( http: HttpClient) { // sobreescritura del método constructor que está en la clase padre
    super(http);
  }
}