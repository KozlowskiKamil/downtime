package com.jabil.downtime.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TechnicianDto {

    private Long id;
    private String name;
    private int badgeNumber;
}
