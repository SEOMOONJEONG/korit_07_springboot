package com.example.todolist.web;

import com.example.todolist.domain.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {
    public class todoController {
        private final TodoService todoService;

        public todoController(TodoService todoService) {
            this.todoService = todoService;
        }

        // 1. 모든 todo 정보 조회(GET /api/todos)
        @GetMapping("/todos")
        public List<Todo> getTodos() {
            return todoService.getTodos();
        }

        // 2. todo 하나 추가(POST /api/todos)
        @PostMapping("/todos")
        public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
            Todo savedTodo = todoService.addTodo(todo);

            return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
        }

        // 3. todo 하나 조회
        @GetMapping("/todos/{id}")
        public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
            return todoService.getTodoById(id)
                    .map(todo -> ResponseEntity.ok().body(todo))
                    .orElse(ResponseEntity.notFound().build());
        }

        // 4. todo 하나 삭제
        @DeleteMapping("/todos/{id}")
        public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
            if(todoService.deleteTodo(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
        // 5. todo 하나 수정
        @PutMapping("/todos/{id}")
        public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
            return todoService.updateTodo(id, todoDetails)
                    .map(updateTodo -> ResponseEntity.ok().body(updateTodo))
                    .orElse(ResponseEntity.notFound().build());
        }
    }


}
