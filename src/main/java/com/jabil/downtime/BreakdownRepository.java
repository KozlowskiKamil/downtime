package com.jabil.downtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaAuditing
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {

//    void findAllByFailureName();
}
