package com.capgemini.wsb.fitnesstracker.statistics.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StatisticsProvider {

    /**
     * Retrieves a statistics based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param statisticsId id of the statistics to be searched
     * @return An {@link Optional} containing the located Statistics, or {@link Optional#empty()} if not found
     */
    Optional<Statistics> getStatistics(Long statisticsId);

    double calculateAverageCaloriesBurned(Long userId);

    void updateStatistics(Long userId, Statistics newStatistics);

    List<Statistics> getStatisticsByUserId(Long userId);

    void deleteStatistics(Long statisticsId);

    List<Statistics> findStatisticsByCaloriesBurnedGreaterThan(int calories);

    List<Statistics> getStatisticsByUserId(Long userId, LocalDate startDate, LocalDate endDate);
}
