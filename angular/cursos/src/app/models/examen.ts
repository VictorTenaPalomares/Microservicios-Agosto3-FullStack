import { Asignatura } from "./asignatura";
import { Generic } from "./generic";
import { Pregunta } from "./pregunta";

export class Examen implements Generic {
    id         : number;
    name       : string;
    createAt   : string;
    preguntas  : Pregunta [] = []; //siempre es importante inicializar los arrays aunque sea a a rray vacío para que después se puedan utilizar directamente
    asignaturaPadre : Asignatura;
    asignaturaHija : Asignatura;
    respondido : boolean;
}
