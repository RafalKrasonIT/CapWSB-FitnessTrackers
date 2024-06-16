package com.capgemini.wsb.fitnesstracker.statistics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsProvider statisticsProvider;

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateStatistics(@PathVariable Long userId, @RequestBody StatisticsDto statisticsDto) {
        Statistics newStatistics = new Statistics();
        newStatistics.setTotalTrainings(statisticsDto.getTotalTrainings());
        newStatistics.setTotalDistance(statisticsDto.getTotalDistance());
        newStatistics.setTotalCaloriesBurned(statisticsDto.getTotalCaloriesBurned());
        newStatistics.setAverageCaloriesBurned(statisticsDto.getAverageCaloriesBurned());

        statisticsProvider.updateStatistics(userId, newStatistics);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<StatisticsDto>> getStatisticsByUserId(@PathVariable Long userId) {
        List<Statistics> statistics = statisticsProvider.getStatisticsByUserId(userId);
        List<StatisticsDto> statisticsDtos = statistics.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(statisticsDtos);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable Long userId) {
        statisticsProvider.deleteStatistics(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calories/{calories}")
    public ResponseEntity<List<StatisticsDto>> findStatisticsByCaloriesBurnedGreaterThan(@PathVariable int calories) {
        List<Statistics> statistics = statisticsProvider.findStatisticsByCaloriesBurnedGreaterThan(calories);
        List<StatisticsDto> statisticsDtos = statistics.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(statisticsDtos);
    }

    private StatisticsDto convertToDto(Statistics statistics) {
        StatisticsDto dto = new StatisticsDto();
        dto.setTotalTrainings(statistics.getTotalTrainings());
        dto.setTotalDistance(statistics.getTotalDistance());
        dto.setTotalCaloriesBurned(statistics.getTotalCaloriesBurned());
        dto.setAverageCaloriesBurned(statistics.getAverageCaloriesBurned());
        return dto;
    }
}



