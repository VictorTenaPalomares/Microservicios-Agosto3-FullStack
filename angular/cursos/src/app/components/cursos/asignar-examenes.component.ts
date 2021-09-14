import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Curso } from 'src/app/models/curso';
import { Examen } from 'src/app/models/examen';
import { CursoService } from 'src/app/services/curso.service';
import { ExamenService } from 'src/app/services/examen.service';
import { map, mergeMap } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-asignar-examenes',
  templateUrl: './asignar-examenes.component.html',
  styleUrls: ['./asignar-examenes.component.css']
})
export class AsignarExamenesComponent implements OnInit {

  curso: Curso;
  autocompleteControl = new FormControl();
  examenesFiltrados: Examen[] = [];
  examenesAsignar: Examen[] = [];
  mostrarColumnas = ['nombre', 'asignatura', 'eliminar'];

  constructor(private route: ActivatedRoute, //inyectamos esta clase para así poder manipular rutas con el id
    private router: Router,
    private cursoService: CursoService,
    private examenService: ExamenService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => { // hacemos que el id que nos viene como string sea un number y lo almacenamos en una constante todo ello 
      const id: number = +params.get('id');
      this.cursoService.ver(id).subscribe(c => { // una vez que tenemos el id nos vamos a buscar el curso a través de él con el cursoService
        this.curso = c;

      });
      this.autocompleteControl.valueChanges.pipe(
        map(valor => typeof valor === 'string' ? valor : valor.name),
        mergeMap(valor => valor ? this.examenService.filtrarPorNombre(valor) : [])
      ).subscribe(examenes => this.examenesFiltrados = examenes);
    })
  }

  mostrarNombre(examen?: Examen): string {
    return examen ? examen.name : '';
  }

  seleccionarExamen(event: MatAutocompleteSelectedEvent): void {
    const examen = event.option.value as Examen; // sacamos el examen del evento autocomplete y lo tratamos como el examen que es
    if (!this.existe(examen.id)) { // usamos el método que va a determinar su existencia que está implementado debajo
      // todas estas instrucciones se van a realizar en caso de que no exista ...
      this.examenesAsignar = this.examenesAsignar.concat(examen);
      console.log(this.examenesAsignar);
      this.autocompleteControl.setValue('');
      event.option.deselect();
      event.option.focus();
    } else {
      Swal.fire(
        'Error',
        `El examen ${examen.name} ya está asignado al curso ${this.curso.nombre}.
        No es posible agregarlo de nuevo...`,
        'error'
      );
    }
  }

  private existe(id: number): boolean { // creamos un método que recibe un number y devuelve un boolean
    let existe = false; // variable local booleana inicializada en false
    this.examenesAsignar.concat(this.curso.examenes) // Concatenaciónde 2 arrays de examen
      .forEach(e => { // como son arrays, le aplicamos el método for each que le podemos aplicar funciones de flecha
        if (id === e.id) { //si el id que recibimos es igual al id de algún examen contenido en la concatenación de las dos listas de examen...
          existe = true; // cambiamos el valor de la variable booleana a true
        }
      });
    return existe; // retorno de la variable cualquiera que sea su estado
  }

  eliminarDelAsignar(examen: Examen): void { // método público (por default) que recibe un argumento de tipo examen y retorna nada, vacío
    this.examenesAsignar = this.examenesAsignar.filter(e => { //como es un flujo reactivo tenemos que plicar el operador de igualdad para que se cree una nueva lista
      examen.id!==e.id // filtramos todos aquellos examenes que sean diferntes al que se está emitiendo
    });
  }


}
