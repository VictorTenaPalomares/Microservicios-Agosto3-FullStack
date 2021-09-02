import { Component } from '@angular/core';

@Component({
  selector: 'app-root', // nombre del selector desde el cual arranca nuestra app
  templateUrl: './app.component.html', // vista
  styleUrls: ['./app.component.css'] // es un array por lo que puede haber 1 o muchos más estilos
})
export class AppComponent {
  title = 'cursos';
}
