import { Component, Directive, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import Swal from 'sweetalert2'
import { Generic } from '../models/generic';
import { CommonService } from '../services/common.service';

@Directive()
export abstract class CommonListarComponent<E extends Generic, S extends CommonService<E>> implements OnInit {

  titulo: string;
  lista: E[];
  protected nombreModel: string;

  totalRegistros: number = 0;
  totalPorPagina: number = 4;
  paginaActual: number = 0;
  pageSizeOptions: number[] = [3, 5, 10, 25, 100];

  @ViewChild(MatPaginator) paginator: MatPaginator; // para inyectar un componente hijo que está dentro del padreen el padre


  //inyección de la capa service en el componente
  constructor(protected service: S) { }

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
        this.lista = p.content as E[]; // este código es el observador que consume al observable, para poder hacerlo nos tenemos que subscribir...
        this.totalRegistros = p.totalElements as number;
        this.paginator._intl.itemsPerPageLabel = "Registros por página"
      });
  }

  public eliminar(e: E): void {
    Swal.fire({
      title: 'Cuidado!?',
      text: `¿Seguro que deseas eliminar al alumno ${e.nombre}?, esta opción no se puede deshacer.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.eliminar(e.id).subscribe(() => {
          this.calcularRangos();
          Swal.fire('Borrado:', `${this.nombreModel} ${e.nombre} eliminado con Éxtito`, 'question');

        });
      }
    });
  }

}
