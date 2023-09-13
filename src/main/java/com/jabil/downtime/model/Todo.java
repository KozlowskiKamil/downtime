package com.jabil.downtime.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

enum Priority {
    HIGH,
    MEDIUM,
    LOW
}

enum Status {
    DONE,
    IN_PROGRES
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

    boolean status;

    LocalDateTime createTask;

    @Enumerated(EnumType.STRING)
    Priority priority;

}
