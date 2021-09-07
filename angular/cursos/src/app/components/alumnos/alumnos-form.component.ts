import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';
import Swal from 'sweetalert2';
import { CommonFormComponent } from '../common-form.component';

@Component({
  selector: 'app-alumnos-form',
  templateUrl: './alumnos-form.component.html',
  styleUrls: ['./alumnos-form.component.css']
})
export class AlumnosFormComponent
  extends CommonFormComponent<Alumno, AlumnoService> implements OnInit {

  private fotoSeleccionada: File;

  constructor(service: AlumnoService,
    router: Router, //clase para enrutar desde angular
    route: ActivatedRoute) { // Proporciona acceso a la información sobre una ruta asociada con un componente, en este caso lo usamos para poder acceder a los parámetros de la ruta
    super(service, router, route)
    this.titulo = 'Crear Alumnos';
    this.model = new Alumno();
    this.redirect = '/alumnos';
    this.nombreModel = Alumno.name;
  }

  public seleccionarFoto(event): void {

    this.fotoSeleccionada = event.target.files[0];
    console.info(this.fotoSeleccionada);

    if (this.fotoSeleccionada.type.indexOf('image') < 0) {
      this.fotoSeleccionada = null;

      Swal.fire(
        'Error al seleccionar la foto',
        'El archivo debe de ser del tipo image',
        'error');
    }
  }

  public crear(): void {
    if (!this.fotoSeleccionada) {
      super.crear();
    } else {
      this.service.crearConFoto(this.model, this.fotoSeleccionada)
        .subscribe(a => {
          console.log(a);
          Swal.fire('Nuevo:', `${this.nombreModel} ${a.nombre} creado con éxito`, 'success');
          this.router.navigate([this.redirect]);
        }, err => {
          if (err.status === 400) {
            this.error = err.error;
            console.log(this.error);
          }
        });
    }
  }

  public editar(): void {
    if (!this.fotoSeleccionada) {
      super.editar();
    } else {
      this.service.editarConFoto(this.model, this.fotoSeleccionada)
        .subscribe(a => {
          console.log(a);
          Swal.fire('Modificado:', `${this.nombreModel} ${a.nombre} actualizado con éxito`, 'success');
          this.router.navigate([this.redirect]);
        }, err => {
          if (err.status === 400) {
            this.error = err.error;
            console.log(this.error);
          }
        });
    }
  }

}