package com.capgemini.wsb.fitnesstracker.statistics.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    List<Statistics> findByUserId(Long userId);
    List<Statistics> findByTotalCaloriesBurnedGreaterThan(int calories);
    List<Statistics> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}