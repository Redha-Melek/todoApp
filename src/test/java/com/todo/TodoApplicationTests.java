package com.todo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.todo.controller.TodoController;
import com.todo.service.TodoService;

@SpringBootTest
class TodoApplicationTests {

	@Autowired
	private TodoController controller;

	@Autowired
	private TodoService todoService;

	@Test
	void contextLoads() {

		Assert.assertNotNull(controller);
		Assert.assertNotNull(todoService);
	}

}
