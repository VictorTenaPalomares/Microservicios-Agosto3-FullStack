import { Alumno } from "./alumno";
import { Examen } from "./examen";
import { Generic } from "./generic";

export class Curso implements Generic {
    id       : number; 
    nombre   : string;
    createAt : string;
    alumnos  : Alumno [] = []; // Este atributo es una lista de objetos, es importante inicializarla a array vacío para que no se convierta en undefined
    examenes : Examen [] = [];
}
