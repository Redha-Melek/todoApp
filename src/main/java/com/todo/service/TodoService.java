package com.todo.service;

import java.util.List;

import com.todo.model.dto.TodoDto;

public interface TodoService {

	public List<TodoDto> getListTodos();

	public List<TodoDto> getTodosByState(boolean state);

	public TodoDto createOrMAJTodo(TodoDto todoDto);

}
