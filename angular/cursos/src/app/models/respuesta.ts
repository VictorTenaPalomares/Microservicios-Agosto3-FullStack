import { Alumno } from "./alumno";
import { Pregunta } from "./pregunta";

export class Respuesta {
    id       : string; // es de tipo string porque se maneja con mongo db
    texto    : string;
    alumno   : Alumno;
    pregunta : Pregunta;
}
