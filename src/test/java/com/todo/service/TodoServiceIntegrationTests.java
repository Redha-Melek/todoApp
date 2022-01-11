package com.todo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.todo.model.Todo;
import com.todo.model.dto.TodoDto;
import com.todo.repository.TodoRepository;
import com.todo.service.impl.TodoServiceImpl;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TodoServiceIntegrationTests {

	@Autowired
	TodoServiceImpl todoService;

	@Autowired
	TodoRepository todoRepository;

	/*
	 * Tests getListTodos
	 */

	@Test
	void findAllTodos_whenTodosEmpty_thenReturnEmpty() throws Exception {

		// Given

		// When
		List<TodoDto> todosDto = todoService.getListTodos();

		// Then
		assertNotNull(todosDto);
		assertTrue(todosDto.isEmpty());

	}

	@Test
	void findAllTodos_whenTodoCreated_thenReturnTodoDto() throws Exception {

		// Given
		String tsString = "2011-10-02 18:48:05.123";
		Timestamp ts = Timestamp.valueOf(tsString);

		createTodo("title", "desc", false, ts);

		// When
		List<TodoDto> todosDto = todoService.getListTodos();

		// Then
		assertNotNull(todosDto);
		assertEquals(1, todosDto.size());

	}

	@Test
	void getTodosByStateTrue_whenTodosDoneAndDone_thenReturnTodoDto() throws Exception {

		// Given
		String tsString = "2011-10-02 18:48:05.123";
		Timestamp ts = Timestamp.valueOf(tsString);

		createTodo("title", "desc", false, ts);

		createTodo("title2", "desc2", true, ts);

		// When
		List<TodoDto> todosDto = todoService.getTodosByState(true);

		// Then
		assertNotNull(todosDto);
		assertEquals(1, todosDto.size());
		TodoDto todoDtoOut = todosDto.iterator().next();
		assertEquals("title2", todoDtoOut.getTitle());
		assertEquals("desc2", todoDtoOut.getDescription());
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
		return todoRepository.save(todo);

	}

}
