import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { Curso } from 'src/app/models/curso';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CursoService } from 'src/app/services/curso.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-asignar-alumnos',
  templateUrl: './asignar-alumnos.component.html',
  styleUrls: ['./asignar-alumnos.component.css']
})
export class AsignarAlumnosComponent implements OnInit {

  curso: Curso;
  alumnosAsignar: Alumno[] = [];
  mostrarColumnas: string[] = ['nombre', 'apellido', 'seleccion'];
  mostrarColumnasAlumnos: string[] = ['id', 'nombre', 'apellido', 'email', 'eliminar'];
  alumnos: Alumno[] = [];
  seleccion: SelectionModel<Alumno> = new SelectionModel<Alumno>(true, []);
  tabIndex = 0;
  dataSource: MatTableDataSource<Alumno>;
  pageSizeOptions: number[] = [3, 5, 10, 20, 50];
  //tenemos que inyectar el paginador que par los alumnos lo tenemos en la vista, no nos viene del backend en este caso
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private route: ActivatedRoute, // para poder ir a buscar información al backend
    private cursoService: CursoService, // inyeccion en el constructor del service de cursos para poder asignar alumnos a los mismos
    private alumnoService: AlumnoService) // inyección del service de alumnos para poder filtrar a los alumnos
  { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id'); // accediendo al id en la ruta recibida y convirtiéndolo a number
      this.cursoService.ver(id).subscribe(c => {
        this.curso = c;
        this.alumnos = this.curso.alumnos;

        this.iniciarPaginador();

      }); // buscamos el curso en el backend y cuando lo extraemos se lo asignamos al atributo curso que hemos declarado en esta clase
    })
  }

  filtrar(nombre: string): void {
    nombre = nombre !== undefined ? nombre.trim() : "";//validamos si el nombre es indefinido, de ser  así le asignamos string vacío, si viene n datos actuamos sobre los espacios en blanco a través del método trim
    if (nombre !== "") {
      this.alumnoService.filtrarPorNombre(nombre)
        .subscribe(alumnos => this.alumnosAsignar = alumnos.filter(a => {
          let filtrar = true;
          this.alumnos.forEach(ca => {
            if (a.id === ca.id) {
              filtrar = false;
            }
          });
          return filtrar;
        }));
    }

  }

  estanTodosSeleccionados(): boolean {
    const seleccionados = this.seleccion.selected.length;
    const numAlumnos = this.alumnosAsignar.length;
    return (seleccionados === numAlumnos);
  }

  seleccionarDesSelecionarTodos(): void {
    if (this.estanTodosSeleccionados()) {
      this.seleccion.clear;
    } else {
      this.alumnosAsignar.forEach(alumno => this.seleccion.select(alumno));
    }
  }

  asignar(): void {
    console.log(this.seleccion.selected);
    this.cursoService.asignarAlumnos(this.curso, this.seleccion.selected)
      .subscribe(c => {
        this.tabIndex = 2;
        Swal.fire(
          'Asignados',
          'Alumnos asignados con exito al curso ' + this.curso.nombre,
          'success'
        );
        this.alumnos = this.alumnos.concat(this.seleccion.selected);
        this.iniciarPaginador();
        this.alumnosAsignar = [];
        this.seleccion.clear();
      },
        e => {
          if (e.status === 500) {
            const mensaje = e.error.message as string;
            if (mensaje.indexOf('ConstraintViolationException') > -1) {
              Swal.fire(
                'Cuidado',
                'No se puede asignar el alumno porque ya está en otro curso',
                'error'
              );
            }
          }
        });
  }

  iniciarPaginador(): void {
    this.dataSource = new MatTableDataSource<Alumno>(this.alumnos);
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = 'Registros por página';

  }

  eliminarAlumno(alumno: Alumno): void {
    Swal.fire({
      title: 'Cuidado!',
      text: `¿Seguro que deseas eliminar al alumno ${alumno.nombre}?, esta opción no se puede deshacer.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.cursoService.eliminarAlumno(this.curso, alumno)

          .subscribe(curso => { // aquí se emite el curso 
            this.alumnos = this.alumnos.filter(a => a.id !== alumno.id); //filtramos por el id de todos los alumnos del curso, si son diferentes al id delque estamos evaluandolos dejamos pasar, el resultado es que estamos comparando se queda fuera   
            this.iniciarPaginador();
            Swal.fire(
              'Eliminado',
              'Alumno' + alumno.nombre + 'eliminado con éxito del curso' + curso.nombre,
              'success'
            );
          });
      }
    });
  }




























}
