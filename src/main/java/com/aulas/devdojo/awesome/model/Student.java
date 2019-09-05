package com.aulas.devdojo.awesome.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Student extends AbstractEntity {
	@NotEmpty(message = "O campo nome é obrigátorio")
	private String name;

	@NotEmpty
	@Email
	private String email;

	public Student() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Student(String nome) {
		this.name = nome;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
	}

}