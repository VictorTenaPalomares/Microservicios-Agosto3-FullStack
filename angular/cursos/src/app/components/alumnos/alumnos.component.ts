import { Component, OnInit, ViewChild } from '@angular/core';
import { BASE_ENDPOINT } from '../../config/app';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CommonListarComponent } from '../common-listar.component';
@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent
  extends CommonListarComponent<Alumno, AlumnoService> implements OnInit {

 baseEnpoint = BASE_ENDPOINT + '/alumnos';

  //inyección de la capa service en el componente
  constructor(service: AlumnoService) {
    super(service);
    this.titulo = 'Listado de Alumnos';
    this.nombreModel = Alumno.name;

  }
}

