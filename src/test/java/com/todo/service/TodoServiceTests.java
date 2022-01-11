package com.todo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.todo.model.Todo;
import com.todo.model.dto.TodoDto;
import com.todo.repository.TodoRepository;
import com.todo.service.impl.TodoServiceImpl;

@SpringBootTest
class TodoServiceTests {

	@InjectMocks
	TodoServiceImpl todoService;

	@Mock
	TodoRepository todoRepository;

	@Mock
	ModelMapper modelMapper;

	/*
	 * Tests calculDatePassed
	 */

	@Test
	void calculDatePassed_whenDateBeforeToday_thenTrue() throws Exception {

		// Given
		String tsString = "2011-10-02 18:48:05.123";
		Timestamp ts = Timestamp.valueOf(tsString);

		// When
		boolean result = todoService.calculDatePassed(ts);

		// Then
		assertTrue(result);
	}

	@Test
	void calculDatePassed_whenDateAfterToday_thenFalse() throws Exception {

		// Given
		// + 1h
		Timestamp ts = new Timestamp(System.currentTimeMillis() + 3600000);

		// When
		boolean result = todoService.calculDatePassed(ts);

		// Then
		assertFalse(result);
	}

	@Test
	void calculDatePassed_whenDateNull_thenFalse() throws Exception {

		// Given
		// When
		boolean result = todoService.calculDatePassed(null);

		// Then
		assertTrue(result);
	}

	/*
	 * Tests getListTodos
	 */

	@Test
	void findAllTodos_whenTodosEmpty_thenEmpty() throws Exception {

		// Given
		List<Todo> todos = new ArrayList<>();
		Mockito.when(todoRepository.findAll()).thenReturn(todos);

		// When
		List<TodoDto> todosDto = todoService.getListTodos();

		// Then
		assertNotNull(todosDto);
		assertTrue(todosDto.isEmpty());

	}

	@Test
	void findAllTodos_whenTodosWithDateBeforeToday_thenReturnTodoDto() throws Exception {

		// Given
		String tsString = "2011-10-02 18:48:05.123";
		Timestamp ts = Timestamp.valueOf(tsString);

		Todo todo = createTodo("title", "desc", false, ts);
		TodoDto todoDto = createTodoDto(todo);

		List<Todo> todos = Arrays.asList(todo);
		Mockito.when(todoRepository.findAll()).thenReturn(todos);
		Mockito.when(modelMapper.map(todo, TodoDto.class)).thenReturn(todoDto);

		// When
		List<TodoDto> todosDto = todoService.getListTodos();

		// Then
		assertNotNull(todosDto);
		assertEquals(1, todosDto.size());
		TodoDto todoDtoOut = todosDto.iterator().next();
		assertEquals("title", todoDtoOut.getTitle());
		assertEquals("desc", todoDtoOut.getDescription());
		assertEquals(false, todoDtoOut.isState());
		assertEquals(ts, todoDtoOut.getDateTodo());
		assertEquals(true, todoDtoOut.isDatePassed());

	}

	@Test
	void findAllTodos_whenTodosWithDateAfterToday_thenReturnTodoDto() throws Exception {

		// Given
		// + 1h
		Timestamp ts = new Timestamp(System.currentTimeMillis() + 3600000);

		Todo todo = createTodo("title", "desc", false, ts);
		TodoDto todoDto = createTodoDto(todo);

		List<Todo> todos = Arrays.asList(todo);
		Mockito.when(todoRepository.findAll()).thenReturn(todos);
		Mockito.when(modelMapper.map(todo, TodoDto.class)).thenReturn(todoDto);

		// When
		List<TodoDto> todosDto = todoService.getListTodos();

		// Then
		assertNotNull(todosDto);
		assertEquals(1, todosDto.size());
		TodoDto todoDtoOut = todosDto.iterator().next();
		assertEquals("title", todoDtoOut.getTitle());
		assertEquals("desc", todoDtoOut.getDescription());
		assertEquals(false, todoDtoOut.isState());
		assertEquals(ts, todoDtoOut.getDateTodo());
		assertEquals(false, todoDtoOut.isDatePassed());

	}

	@Test
	void getTodosByStateTrue_whenTodosDoneAndDone_thenReturnTodoDto() throws Exception {

		// Given
		String tsString = "2011-10-02 18:48:05.123";
		Timestamp ts = Timestamp.valueOf(tsString);

		Todo todo = createTodo("title", "desc", false, ts);

		Todo todo2 = createTodo("title", "desc", true, ts);
		TodoDto todoDto2 = createTodoDto(todo2);

		List<Todo> todos = Arrays.asList(todo2);
		Mockito.when(todoRepository.findByState(true)).thenReturn(todos);
		Mockito.when(modelMapper.map(todo2, TodoDto.class)).thenReturn(todoDto2);

		// When
		List<TodoDto> todosDto = todoService.getTodosByState(true);

		// Then
		assertNotNull(todosDto);
		assertEquals(1, todosDto.size());
		TodoDto todoDtoOut = todosDto.iterator().next();
		assertEquals("title", todoDtoOut.getTitle());
		assertEquals("desc", todoDtoOut.getDescription());
		assertEquals(true, todoDtoOut.isState());
		assertEquals(ts, todoDtoOut.getDateTodo());
		assertEquals(true, todoDtoOut.isDatePassed());

	}

	private Todo createTodo(String title, String description, boolean state, Timestamp dateTodo) {
		Todo todo = new Todo();
		todo.setTitle(title);
		todo.setDescription(description);
		todo.setState(state);
		todo.setDateTodo(dateTodo);
		return todo;
	}

	private TodoDto createTodoDto(Todo todo) {
		TodoDto todoDto = new TodoDto();
		todoDto.setTitle(todo.getTitle());
		todoDto.setDescription(todo.getDescription());
		todoDto.setState(todo.isState());
		todoDto.setDateTodo(todo.getDateTodo());
		return todoDto;
	}

}
