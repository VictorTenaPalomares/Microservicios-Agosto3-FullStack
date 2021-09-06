import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-alumnos-form',
  templateUrl: './alumnos-form.component.html',
  styleUrls: ['./alumnos-form.component.css']
})
export class AlumnosFormComponent implements OnInit {

  titulo = "Crear Alumnos";
  alumno: Alumno = new Alumno(); // instanciamos la clase alumno con la información introducida en el campo correspondiente del formulario
  error: any;

  constructor(private service: AlumnoService,
    private router: Router, //clase para enrutar desde angular
    private route: ActivatedRoute) // Proporciona acceso a la información sobre una ruta asociada con un componente, en este caso lo usamos para poder acceder a los parámetros de la ruta
  { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id'); // declaramos  constante de tipo number y la igualamos a el get del parámetro que se emite en la funcion de flecha
      if (id) this.service.ver(id).subscribe(al => this.alumno = al)
    });
  }
  public crear(): void {
    this.service.crear(this.alumno).subscribe(a => {
      console.log(a);
      Swal.fire('Nuevo:', `Alumno ${a.nombre} creado con éxito`, 'success');
      this.router.navigate(['/alumnos']);
    }, err => {
      if (err.status === 400) {
        this.error = err.error;
        console.log(this.error);
      }
    });
  }

  public editar(): void {
    this.service.editar(this.alumno).subscribe(a => {
      console.log(a);
      Swal.fire('Modificado:', `Alumno ${a.nombre} editado con éxito`, 'success');
      this.router.navigate(['/alumnos']);
    }, err => {
      if (err.status === 400) {
        this.error = err.error;
        console.log(this.error);
      }
    });
  }



}
