package com.capgemini.wsb.fitnesstracker.statistics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticsProviderImpl implements StatisticsProvider {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public Optional<Statistics> getStatistics(Long statisticsId) {
        return statisticsRepository.findById(statisticsId);
    }

    @Override
    public double calculateAverageCaloriesBurned(Long userId) {
        List<Statistics> stats = statisticsRepository.findByUserId(userId);
        return stats.stream()
                .mapToInt(Statistics::getTotalCaloriesBurned)
                .average()
                .orElse(0);
    }

    @Override
    public void updateStatistics(Long userId, Statistics newStatistics) {
        List<Statistics> existingStatistics = getStatisticsByUserId(userId);
        if (!existingStatistics.isEmpty()) {
            Statistics stats = existingStatistics.get(0);
            stats.setTotalTrainings(newStatistics.getTotalTrainings());
            stats.setTotalDistance(newStatistics.getTotalDistance());
            stats.setTotalCaloriesBurned(newStatistics.getTotalCaloriesBurned());
            stats.setAverageCaloriesBurned(newStatistics.getAverageCaloriesBurned());
            statisticsRepository.save(stats);
        } else {
            statisticsRepository.save(newStatistics);
        }
    }

    @Override
    public List<Statistics> getStatisticsByUserId(Long userId) {
        return statisticsRepository.findByUserId(userId);
    }

    @Override
    public void deleteStatistics(Long statisticsId) {
        List<Statistics> statistics = statisticsRepository.findByUserId(statisticsId);
        statisticsRepository.deleteAll(statistics);
    }
    @Override
    public List<Statistics> findStatisticsByCaloriesBurnedGreaterThan(int calories) {
        return statisticsRepository.findByTotalCaloriesBurnedGreaterThan(calories);
    }

    @Override
    public List<Statistics> getStatisticsByUserId(Long userId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }
}

