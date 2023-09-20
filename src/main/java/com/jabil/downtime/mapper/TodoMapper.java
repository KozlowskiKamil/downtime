package com.jabil.downtime.mapper;

import com.jabil.downtime.dto.TodoDto;
import com.jabil.downtime.model.Todo;
import org.springframework.stereotype.Service;

@Service
public class TodoMapper {

    public Todo fromDto(TodoDto todoDto) {
        return Todo.builder()
                .id(todoDto.getId())
                .task(todoDto.getTask())
                .client(todoDto.getClient())
                .priority(todoDto.getPriority())
                .build();
    }

    public TodoDto toTodoDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .task(todo.getTask())
                .client(todo.getClient())
                .priority(todo.getPriority())
                .build();
    }
}