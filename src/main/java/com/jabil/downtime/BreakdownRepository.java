package com.jabil.downtime;

import com.jabil.downtime.model.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaAuditing
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {

        Breakdown findBreakdownById(Long id);

        List<Breakdown> findAllByOngoing(boolean ongoing);

        List<Breakdown> findAllByComputerNameAndFailureName(String computerName , String failureName);

}
