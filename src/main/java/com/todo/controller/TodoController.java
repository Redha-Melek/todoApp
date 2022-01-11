package com.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.todo.model.dto.TodoDto;
import com.todo.service.TodoService;

@RestController
public class TodoController {

	@Autowired
	TodoService todoService;

	@GetMapping("/")
	public ModelAndView homePage(Model model) {
		List<TodoDto> todos = todoService.getListTodos();
		List<TodoDto> todosDone = todoService.getTodosByState(true);
		List<TodoDto> todosNonDone = todoService.getTodosByState(false);

		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("todos", todos);
		modelAndView.addObject("todosDone", todosDone);
		modelAndView.addObject("todosNonDone", todosNonDone);

		return modelAndView;
	}

	/*
	 * Méthode qui permet de récupérer les rappels
	 * 
	 * @return TodoDto
	 */
	@GetMapping("/todos")
	public List<TodoDto> getTodos() {
		return todoService.getListTodos();
	}

	/*
	 * Méthode qui permet de récupérer les rappels selon le state
	 * 
	 * @return TodoDto
	 */
	@GetMapping("/todos/{state}")
	public List<TodoDto> getTodosByState(@PathVariable("state") boolean state) {
		return todoService.getTodosByState(state);
	}
}