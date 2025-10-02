package com.example.todolist.service;

import com.example.todolist.domain.Todo;
import com.example.todolist.domain.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;


    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // 1. todo 전체 조회
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    // 2. 새로운 todo 저장
    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    // 3. todo 하나 조회
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    // 4. todo 하나 삭제
    public boolean deleteTodo(Long id) {
        if(todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    // 5. todo 수정
    @Transactional
    public Optional<Todo> updateTodo(Long id, Todo todoDetails) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setContent(todoDetails.getContent());
                    todo.setCompleted(todoDetails.isCompleted());
                    return todo;
                });
    }
    /*
    // 특정 id의 할 일 완료 상태를 토글하는 메서드
    @Transactional
    public Optional<Todo> updateTodoStatus(Long id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setCompleted(!todo.isCompleted());  // 현재 상태 반전
                    return todo;
                    // @Transactional 덕분에 변경 감지 후 자동 저장됨
                });
    }
    */

}

