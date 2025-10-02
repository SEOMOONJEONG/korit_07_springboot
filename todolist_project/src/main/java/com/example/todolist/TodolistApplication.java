package com.example.todolist;

import com.example.todolist.domain.AppUser;
import com.example.todolist.domain.AppUserRepository;
import com.example.todolist.domain.Todo;
import com.example.todolist.domain.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplication {
	private static final Logger logger = LoggerFactory.getLogger(
			TodolistApplication.class
	);

	private final TodoRepository todoRepository;
	private final AppUserRepository userRepository;

    public TodolistApplication(TodoRepository todoRepository, AppUserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }


    public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

}
