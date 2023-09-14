package com.jabil.downtime.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public enum Priority {
    HIGH,
    MEDIUM,
    LOW
}

public enum Status {
    DONE,
    IN_PROGRES,
    WAITING
}

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String task;

    String client;

    Status status;

    LocalDateTime createTask;

    @Enumerated(EnumType.STRING)
    Priority priority;

}