import { Component, Directive, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2'
import { Generic } from '../models/generic';
import { CommonService } from '../services/common.service';

@Directive()
export abstract class CommonFormComponent<E extends Generic,S extends CommonService<E>> implements OnInit {

  titulo:string;
  model: E ; // instanciamos la clase alumno con la información introducida en el campo correspondiente del formulario
  error: any;
  protected redirect: string;
  protected nombreModel: string;

  constructor(protected service: S,
              protected router: Router, //clase para enrutar desde angular
              protected route: ActivatedRoute) // Proporciona acceso a la información sobre una ruta asociada con un componente, en este caso lo usamos para poder acceder a los parámetros de la ruta
  { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id'); // declaramos  constante de tipo number y la igualamos a el get del parámetro que se emite en la funcion de flecha
      if (id) this.service.ver(id).subscribe(m => {
        this.model = m;
        this.titulo='Editar ' + this.nombreModel;
      });
    });
  }
  
  public crear(): void {
    this.service.crear(this.model).subscribe(a => {
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

  public editar(): void {
    this.service.editar(this.model).subscribe(a => {
      console.log(a);
      Swal.fire('Modificado:', `${this.nombreModel} ${a.nombre} editado con éxito`, 'success');
      this.router.navigate([this.redirect]);
    }, err => {
      if (err.status === 400) {
        this.error = err.error;
        console.log(this.error);
      }
    });
  }



}
