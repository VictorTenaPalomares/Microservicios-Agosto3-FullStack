package com.formacionviewnext.microservicios.app.commons.alumnos.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "alumnos")
public class Alumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob //permite persistir un objeto de tamaño grande-> Large Object
	@JsonIgnore // para no mostrar esta información en el json
	private byte[] foto;

	@NotEmpty
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	@Email
	private String email;

	@Column(name = "create_at") // le damos el nombre a la columna que se forma
	@Temporal(TemporalType.TIMESTAMP) // para decirle que almacene la fecha completa: años, mes, dia y hora
	private Date createAt;

	// Esto lo hacemos para asignar la fecha a tipo date antes de ser almacenado en
	// la BDD
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	//Método para que retorne un identificador de la foto, será utilizado por Angular
	public Integer getFotoHashCode() {
		
		// validamos para que, en caso de que foto sea null, no llegue a hashcode y nos lance una excepción
		if (this.foto==null) {
			return null;
		}
		
		 return this.foto.hashCode(); //este método lo proporciona la clase Object, es un identificador que tienen todos los objetos java y que los hace únicos
		 
		 // forma de hacer lo mismo pero con el operador ternario
		 //return(this.foto!=null)?this.foto.hashCode():null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Alumno)) {
			return false;
		}

		Alumno a = (Alumno) obj;

		return this.id != null && this.id.equals(a.getId());
	}

}
