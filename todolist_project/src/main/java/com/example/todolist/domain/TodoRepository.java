package com.example.todolist.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 완료된 Todo 삭제용
    void deleteByAppUserIdAndIsCompletedTrue(Long userId);

    // 완료된 Todo 목록 조회용 (선택적)
    List<Todo> findByAppUserIdAndIsCompletedTrue(Long userId);
}
