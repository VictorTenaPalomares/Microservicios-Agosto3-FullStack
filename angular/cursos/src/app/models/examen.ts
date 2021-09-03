import { Asignatura } from "./asignatura";
import { Pregunta } from "./pregunta";

export class Examen {
    id         : number;
    name       : string;
    createAt   : string;
    preguntas  : Pregunta [] = [];
    asignatura : Asignatura;
    respondido : boolean;
}
