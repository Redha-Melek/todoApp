package com.todo.model.dto;

import java.sql.Timestamp;

import lombok.Data;

/*
 * TodoDto classe qui permet la transition des données
 */
public @Data class TodoDto {

	private Long id;

	private String title;

	private boolean state;

	private String description;

	private Timestamp dateTodo;

	private boolean datePassed = false;

}
