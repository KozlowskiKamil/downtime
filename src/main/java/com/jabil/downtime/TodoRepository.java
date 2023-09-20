package com.jabil.downtime;

import com.jabil.downtime.dto.TodoDto;
import com.jabil.downtime.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {


    void deleteAllById(Long taskId);




}