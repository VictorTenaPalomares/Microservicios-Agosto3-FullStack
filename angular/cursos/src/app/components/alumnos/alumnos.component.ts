import { Component, OnInit } from '@angular/core';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';

@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent implements OnInit {

  titulo = 'Listado de Alumnos';
  alumnos: Alumno[];


  //inyección de la capa service en el componente
  constructor(private service: AlumnoService) { }

  // todas las comunicaciones que se hacen con el backend se tienen que implementar en el ngOnInit
  ngOnInit(): void {
    this.service.listar().subscribe(alumnos => { //Sacamos los alumnos que nos han venido desde el backend y se los pasamos al atributo de esta clase
      this.alumnos = alumnos; // este código es el observador que consume al observable, para poder hacerlo nos tenemos que subscribir...
    });

  }

}
