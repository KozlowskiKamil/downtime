package com.jabil.downtime.dto;

import com.jabil.downtime.model.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
