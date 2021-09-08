import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscriber } from 'rxjs';
import { Asignatura } from 'src/app/models/asignatura';
import { Examen } from 'src/app/models/examen';
import { Pregunta } from 'src/app/models/pregunta';
import { ExamenService } from 'src/app/services/examen.service';
import Swal from 'sweetalert2';
import { CommonFormComponent } from '../common-form.component';

@Component({
  selector: 'app-examen-form',
  templateUrl: './examen-form.component.html',
  styleUrls: ['./examen-form.component.css']
})
export class ExamenFormComponent extends CommonFormComponent<Examen, ExamenService>
  implements OnInit {

  asignaturasPadre: Asignatura[] = [];

  asignaturasHija: Asignatura[] = [];

  errorPreguntas :string;


  constructor(service: ExamenService,
    router: Router,
    route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Examen';
    this.model = new Examen();
    this.nombreModel = Examen.name;
    this.redirect = '/examenes';

  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id'); // declaramos  constante de tipo number y la igualamos a el get del parámetro que se emite en la funcion de flecha
      if (id) this.service.ver(id).subscribe(m => {
        this.model = m;
        this.titulo = 'Editar ' + this.nombreModel;

        this.cargarHijos();
      });
    });

    this.service.findAllAsignatura().subscribe(asignaturas => this.asignaturasPadre = asignaturas.filter(a => !a.padre));
  }

  public crear(): void {
    if (this.model.preguntas.length===0) {
      this.errorPreguntas='El examen debe tener preguntas';
      //Swal.fire('Error, preguntas vacías','El examen debe tener preuntas','error');
      return; // como es void rompemos la ejecución del método 
    }
    this.errorPreguntas=undefined;
    this.eliminarPreguntasVacias();
    super.crear();
  }

  public editar(): void {
    if (this.model.preguntas.length===0) {
      this.errorPreguntas='El examen debe tener preguntas';
      //Swal.fire('Error, preguntas vacías','El examen debe tener preuntas','error');
      return; // como es void rompemos la ejecución del método 
    }
    this.errorPreguntas=undefined;
    this.eliminarPreguntasVacias();
    super.editar();

  }





  cargarHijos(): void {
    this.asignaturasHija = this.model.asignaturaPadre ?
      this.model.asignaturaPadre.hijos :
      [];
  }

  compararAsignatura(a1: Asignatura, a2: Asignatura): boolean {

    if (a1 === undefined && a2 === undefined) { // para que no lance un error
      return true;
    }

    if (a1 === null || a2 === null || a1 === undefined || a2 || undefined) { // cualquiera de ellas no existe, no tiene la instancia
      return false;
    }

    if (a1.id === a2.id) {
      return true;
    }
    return null;// esto no va, pero para que no de falle se ha incluido, seguimos a delante
  }

  agregarPregunta(): void {
    this.model.preguntas.push(new Pregunta()); //agregamos a la lista de preguntas del examen el que? pues una nueva instancia de pregunta
  }

  asignarTexto(pregunta: Pregunta, event: any): void {
    pregunta.texto = event.target.value as string; //asignamos el texto que viene escrito como evento a la pregunta para que se pueble en el backend
    console.log(this.model);
  }

  eliminarPregunta(pregunta: Pregunta) {
    this.model.preguntas = this.model.preguntas.filter(p => pregunta.texto !== p.texto);
    // si el texto de la lista de preguntas es distinto a el texto de la pregunta que evaluamos lo dejamos pasar...

  }

  eliminarPreguntasVacias(): void {
    this.model.preguntas = this.model.preguntas.filter(p => p.texto != null && p.texto.length > 0); // todas las preguntas que sean nulas o vacías se van a quitar...
  }




}
