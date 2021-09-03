import { Alumno } from "./alumno";
import { Examen } from "./examen";

export class Curso {
    id       : number; 
    nombre   : string;
    createAt : string;
    alumnos  : Alumno [] = []; // Este atributo es una lista de objetos, es importante inicializarla a array vacío para que no se convierta en undefined
    examenes : Examen [] = [];
}
