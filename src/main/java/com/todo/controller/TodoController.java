package com.todo.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.todo.model.dto.TodoDto;
import com.todo.model.dto.TodoDtoIHM;
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

		modelAndView.addObject("todoIn", new TodoDtoIHM());

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

	/*
	 * Méthode qui permet de créer un rappel depuis le front
	 * 
	 * @return home page
	 */
	@PostMapping("/createTodoForm")
	public ModelAndView createTodoFront(@ModelAttribute TodoDtoIHM todoIn, Model model) {
		String tsTodo = todoIn.getDateOfTodo().replace("T", " ");
		if (tsTodo.length() < 19) {
			tsTodo += ":00";
		}
		todoIn.setDateTodo(Timestamp.valueOf(tsTodo));
		todoService.createOrMAJTodo(todoIn);
		return homePage(model);
	}

	/*
	 * Méthode qui permet de renvoyer le formulaire d'édition d'un rappel
	 * 
	 * @return update form
	 */
	@PostMapping("/updateTodoToForm")
	public ModelAndView updateTodoToForm(@ModelAttribute TodoDtoIHM todoIn, Model model) {

		String dateTimeTodo = todoIn.getDateTodo().toString().replace(" ", "T");
		todoIn.setDateOfTodo(dateTimeTodo);

		ModelAndView modelAndView = new ModelAndView("edit");
		modelAndView.addObject("todoIn", todoIn);

		return modelAndView;
	}

	/*
	 * Méthode qui permet de créer ou mettre à jour un rappel
	 * 
	 * @return TodoDto
	 */
	@PutMapping("/todos")
	public TodoDto createOrMettreAJourTodo(@RequestBody TodoDto todo) {
		return todoService.createOrMAJTodo(todo);
	}
}