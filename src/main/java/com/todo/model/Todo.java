package com.todo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/*
 * Todo classe qui permet l'object oriented mapping avec la table todo
 */
@Entity
public @Data class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private boolean state = false;

	@Column
	private String description;

	@Column
	private Timestamp dateTodo;
}