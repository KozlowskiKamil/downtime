package com.jabil.downtime;

import com.jabil.downtime.dto.TodoDto;
import com.jabil.downtime.mapper.TodoMapper;
import com.jabil.downtime.model.Priority;
import com.jabil.downtime.model.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public List<TodoDto> findAllTodo() {
        return todoRepository.findAll()
                .stream()
                .map(todoMapper::toTodoDto)
                .collect(Collectors.toList());
    }

    public TodoDto saveToDo(TodoDto todoDto) {
        Todo saveTodo = todoRepository.save(todoMapper.fromDto(todoDto));
        return todoMapper.toTodoDto(saveTodo);
    }

    public void deleteTask(Long taskId) {
        todoRepository.deleteById(taskId);
    }

    public List<TodoDto> findAllByPriority(Priority priority) {
        List<Todo> todosByPriority = todoRepository.findAllByPriority(priority);
        return todosByPriority.stream()
                .map(todoMapper::toTodoDto)
                .collect(Collectors.toList());
    }
}