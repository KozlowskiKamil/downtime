package com.jabil.downtime.model;

import com.jabil.downtime.dto.BreakdownDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Technician {

    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL)
    private List<Breakdown> breakdownList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "badge_number")
    private int badgeNumber;

}