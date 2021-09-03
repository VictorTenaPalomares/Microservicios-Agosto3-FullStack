export class Alumno {
    id          : number; // por defecto el modificador de acceso en angular es public
    nombre      : string;
    apellido    : string;
    email       : string;
    createAt    : string;
    fotoHashCode: number;
} 
//en angular replicamos los entity del backend que se generan a través de los getters