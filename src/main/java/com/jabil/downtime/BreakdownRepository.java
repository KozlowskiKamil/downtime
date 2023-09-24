package com.jabil.downtime;

import com.jabil.downtime.model.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@EnableJpaAuditing
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {

    Breakdown findBreakdownById(Long id);

    List<Breakdown> findAllByOngoing(boolean ongoing);

    List<Breakdown> findAllByOngoingOrderByFailureStartTimeDesc(boolean ongoing);

    List<Breakdown> findAllByComputerNameAndFailureNameOrderByFailureStartTimeDesc(String computerName, String failureName);

    @Query("SELECT b.computerName, COUNT(b.computerName) AS computerCount FROM Breakdown b GROUP BY b.computerName ORDER BY computerCount DESC")
    List<Object[]> findComputerNamesWithCounts();

    @Query("SELECT b.failureName, COUNT(b.failureName) AS failureCount FROM Breakdown b GROUP BY b.failureName ORDER BY failureCount DESC")
    List<Object[]> findFailureNameWithCounts();


    @Query("SELECT b.failureName, COUNT(b) AS failureCount " +
            "FROM Breakdown b " +
            "WHERE b.failureStartTime >= :startDate " +
            "  AND b.failureStartTime <= :endDate " +
            "GROUP BY b.failureName " +
            "ORDER BY failureCount DESC")
    List<Object[]> findTopFailureNamesWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT b.failureName, COUNT(b.failureName) AS failureCount FROM Breakdown b WHERE DATE(b.failureStartTime) = CURRENT_DATE GROUP BY b.failureName ORDER BY failureCount DESC")
    List<Object[]> findTopFailureNamesToday();

    @Query("SELECT b FROM Breakdown b " +
            "WHERE b.waitingTime = (SELECT MAX(b2.waitingTime) FROM Breakdown b2 WHERE b2.computerName = b.computerName) " +
            "ORDER BY b.waitingTime DESC")
    List<Breakdown> findAllSortedByMaxWaitingTimeForComputerName();

    @Query("SELECT b FROM Breakdown b " + "ORDER BY b.counter DESC")
    List<Breakdown> findAllSortedByMaxCounterForFailureName();

}