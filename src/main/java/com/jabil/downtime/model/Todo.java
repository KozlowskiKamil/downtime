package com.jabil.downtime.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    Status status;

    LocalDateTime createTask;

    @Enumerated(EnumType.STRING)
    Priority priority;

}