package com.jabil.downtime.dto;

import com.jabil.downtime.model.Priority;
import com.jabil.downtime.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    Long id;
    String task;
    String client;
    Priority priority;
}
