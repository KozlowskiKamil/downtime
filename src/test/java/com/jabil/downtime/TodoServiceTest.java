package com.jabil.downtime;

import com.jabil.downtime.dto.TodoDto;
import com.jabil.downtime.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @MockBean
    private TodoRepository todoRepository;


    @Test
    @Transactional
    void testFindAllTodo() {
        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo());
        todoList.add(new Todo());
        when(todoRepository.findAll()).thenReturn(todoList);
        List<TodoDto> result = todoService.findAllTodo();
        assertEquals(2, result.size());
        verify(todoRepository, times(1)).findAll();
    }
}