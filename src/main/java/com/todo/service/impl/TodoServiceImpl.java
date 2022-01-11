package com.todo.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.model.dto.TodoDto;
import com.todo.repository.TodoRepository;
import com.todo.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TodoRepository todoRepository;

	/*
	 * Méthode qui permet de récupérer tous les rappels
	 * 
	 * @return List<TodoDto>
	 */
	@Override
	public List<TodoDto> getListTodos() {

		// Récupération et conversion de la liste des rappels vers des dto
		List<TodoDto> todosDto = todoRepository.findAll().stream().map(todo -> modelMapper.map(todo, TodoDto.class))
				.collect(Collectors.toList());

		// Valoriser le champ datePassed et retourner la liste des rappels
		todosDto.stream().forEach(todo -> todo.setDatePassed(calculDatePassed(todo.getDateTodo())));
		return todosDto;
	}

	/*
	 * Méthode qui permet de récupérer des rappels selon le state
	 * 
	 * @return List<TodoDto>
	 */
	@Override
	public List<TodoDto> getTodosByState(boolean state) {

		// Récupération et conversion de la liste des rappels vers des dto
		List<TodoDto> todosDto = todoRepository.findByState(state).stream()
				.map(todo -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());

		// Valoriser le champ datePassed et retourner la liste des rappels
		todosDto.stream().forEach(todo -> todo.setDatePassed(calculDatePassed(todo.getDateTodo())));
		return todosDto;
	}

	/*
	 * Vérifier si la dateTodo est passé
	 */
	public boolean calculDatePassed(final Timestamp dateTodo) {
		return dateTodo == null || dateTodo.before(new Timestamp(System.currentTimeMillis()));
	}

}