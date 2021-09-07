import { Asignatura } from "./asignatura";
import { Generic } from "./generic";
import { Pregunta } from "./pregunta";

export class Examen implements Generic {
    id         : number;
    name       : string;
    createAt   : string;
    preguntas  : Pregunta [] = [];
    asignatura : Asignatura;
    respondido : boolean;
}
