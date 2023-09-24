package com.jabil.downtime;

import com.jabil.downtime.model.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Query("SELECT b.failureName, COUNT(b.failureName) AS failureCount FROM Breakdown b WHERE DATE(b.failureStartTime) BETWEEN :lastWeekStartDate AND :lastWeekEndDate GROUP BY b.failureName ORDER BY failureCount DESC")
    List<Object[]> findTopFailureNamesLastWeek(@Param("lastWeekStartDate") LocalDate lastWeekStartDate, @Param("lastWeekEndDate") LocalDate lastWeekEndDate);

    @Query("SELECT b.failureName, COUNT(b.failureName) AS failureCount FROM Breakdown b WHERE DATE(b.failureStartTime) BETWEEN :lastMonthStartDate AND :lastMonthEndDate GROUP BY b.failureName ORDER BY failureCount DESC")
    List<Object[]> findTopFailureNamesLastMonth(@Param("lastMonthStartDate") LocalDate lastMonthStartDate, @Param("lastMonthEndDate") LocalDate lastMonthEndDate);


    @Query("SELECT b.failureName, SUM(b.counter) AS totalCounter FROM Breakdown b WHERE DATE(b.failureStartTime) = CURRENT_DATE GROUP BY b.failureName ORDER BY totalCounter DESC")
    List<Object[]> findCounterToday();

    @Query("SELECT b.failureName, SUM(b.counter) AS totalCounter FROM Breakdown b WHERE DATE(b.failureStartTime) BETWEEN :lastWeekStartDate AND :lastWeekEndDate GROUP BY b.failureName ORDER BY totalCounter DESC")
    List<Object[]> findCounterLastWeek(@Param("lastWeekStartDate") LocalDate lastWeekStartDate, @Param("lastWeekEndDate") LocalDate lastWeekEndDate);


    @Query("SELECT b.failureName, SUM(b.counter) AS totalCounter FROM Breakdown b WHERE DATE(b.failureStartTime) BETWEEN :lastMonthStartDate AND :lastMonthEndDate GROUP BY b.failureName ORDER BY totalCounter DESC")
    List<Object[]> findCounterLastMonth(@Param("lastMonthStartDate") LocalDate lastMonthStartDate, @Param("lastMonthEndDate") LocalDate lastMonthEndDate);




//    @Query("SELECT t, COUNT(b) AS breakdownCount FROM Technician t LEFT JOIN t.breakdowns b GROUP BY t ORDER BY breakdownCount DESC")
//    List<Object[]> findTechniciansOrderByBreakdownCount();



//    @Query("SELECT t, COUNT(b) AS breakdownCount FROM Technician t LEFT JOIN t.breakdowns b GROUP BY t ORDER BY breakdownCount DESC")
//    List<Object[]> findTechniciansOrderByBreakdownCount();
//
//
//    @Query("SELECT t, SUM(b.waitingTime) AS totalWaitingTime FROM Technician t LEFT JOIN t.breakdowns b GROUP BY t ORDER BY totalWaitingTime DESC")
//    List<Object[]> findTechniciansOrderByWaitingTime();
//
//
//    @Query("SELECT t, SUM(b.counter) AS totalCounter FROM Technician t LEFT JOIN t.breakdowns b GROUP BY t ORDER BY totalCounter DESC")
//    List<Object[]> findTechniciansOrderByCounter();


}