import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';
import Swal from 'sweetalert2'
@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent implements OnInit {

  titulo = 'Listado de Alumnos';
  alumnos: Alumno[];
  totalRegistros: number = 0;
  totalPorPagina: number = 4;
  paginaActual: number = 0;
  pageSizeOptions: number[] = [3, 5, 10, 25, 100];

  @ViewChild(MatPaginator) paginator: MatPaginator; // para inyectar un componente hijo que está dentro del padreen el padre


  //inyección de la capa service en el componente
  constructor(private service: AlumnoService) { }

  // todas las comunicaciones que se hacen con el backend se tienen que implementar en el ngOnInit
  ngOnInit(): void {
    this.calcularRangos();
  }

  public paginar(event: PageEvent): void {
    this.paginaActual = event.pageIndex;
    this.totalPorPagina = event.pageSize;
    this.calcularRangos();


  }

  private calcularRangos() {
    this.service.listarPaginas(this.paginaActual.toString(),
      this.totalPorPagina.toString()).subscribe(p => { //Sacamos los alumnos que nos han venido desde el backend y se los pasamos al atributo de esta clase. En la opción paginable convertimos al vuelo el number a string dentro del paso de parámetros
        this.alumnos = p.content as Alumno[]; // este código es el observador que consume al observable, para poder hacerlo nos tenemos que subscribir...
        this.totalRegistros = p.totalElements as number;
        this.paginator._intl.itemsPerPageLabel="Registros por página"
      });
  }

  public eliminar(alumno: Alumno): void {
    Swal.fire({
      title: 'Cuidado!?',
      text: `¿Seguro que deseas eliminar al alumno ${alumno.nombre}?, esta opción no se puede deshacer.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.eliminar(alumno.id).subscribe(() => {
          this.calcularRangos();
          //this.alumnos = this.alumnos.filter(alumnoQueSeHaQuedado => {
          //alumnoQueSeHaQuedado !== alumno;// El filtrado conservará a todos los alumnos que están en la lista que son diferntes al alumno que se recibe por parámetro
          Swal.fire('Borrado:', `Alumno ${alumno.nombre} eliminado con Éxtito`, 'question');

        });
      }
    });
  }

}
