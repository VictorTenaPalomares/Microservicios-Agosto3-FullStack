import { Component } from '@angular/core';

@Component({
  selector: 'app-root', // nombre del selector desde el cual arranca nuestra app
  templateUrl: './app.component.html', // vista
  styleUrls: ['./app.component.css'] // es un array por lo que puede haber 1 o muchos más estilos
})
export class AppComponent { // Esta clase de TypeScript sirve para que este componente se pureda registrar en otros de la aplicación app.module.ts
  title = 'cursos';
}
