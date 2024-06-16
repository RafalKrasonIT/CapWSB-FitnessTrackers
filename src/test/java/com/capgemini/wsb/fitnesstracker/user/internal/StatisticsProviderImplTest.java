package com.capgemini.wsb.fitnesstracker.user.internal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProviderImpl;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.*;
import java.util.List;


public class StatisticsProviderImplTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private StatisticsProviderImpl statisticsProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateStatistics() {
        Statistics newStatistics = new Statistics();
        newStatistics.setTotalTrainings(10);
        newStatistics.setTotalDistance(50.5);
        newStatistics.setTotalCaloriesBurned(1000);
        newStatistics.setAverageCaloriesBurned(100);

        when(statisticsRepository.findByUserId(1L)).thenReturn(Arrays.asList(newStatistics));
        when(statisticsRepository.findById(1L)).thenReturn(Optional.of(newStatistics));

        statisticsProvider.updateStatistics(1L, newStatistics);

        Optional<Statistics> updatedStatistics = statisticsProvider.getStatistics(1L);
        assertTrue(updatedStatistics.isPresent());
        assertEquals(10, updatedStatistics.get().getTotalTrainings());
        assertEquals(50.5, updatedStatistics.get().getTotalDistance());
        assertEquals(1000, updatedStatistics.get().getTotalCaloriesBurned());
        assertEquals(100, updatedStatistics.get().getAverageCaloriesBurned());
    }

    @Test
    public void testGetStatisticsByUserId() {
        Statistics stat1 = new Statistics();
        stat1.setTotalTrainings(10);
        stat1.setTotalDistance(50.5);
        stat1.setTotalCaloriesBurned(1000);
        stat1.setAverageCaloriesBurned(100);

        Statistics stat2 = new Statistics();
        stat2.setTotalTrainings(20);
        stat2.setTotalDistance(70.5);
        stat2.setTotalCaloriesBurned(2000);
        stat2.setAverageCaloriesBurned(200);

        when(statisticsRepository.findByUserId(1L)).thenReturn(Arrays.asList(stat1, stat2));

        List<Statistics> statistics = statisticsProvider.getStatisticsByUserId(1L);
        assertEquals(2, statistics.size());
        assertEquals(10, statistics.get(0).getTotalTrainings());
        assertEquals(50.5, statistics.get(0).getTotalDistance());
        assertEquals(1000, statistics.get(0).getTotalCaloriesBurned());
        assertEquals(100, statistics.get(0).getAverageCaloriesBurned());

        assertEquals(20, statistics.get(1).getTotalTrainings());
        assertEquals(70.5, statistics.get(1).getTotalDistance());
        assertEquals(2000, statistics.get(1).getTotalCaloriesBurned());
        assertEquals(200, statistics.get(1).getAverageCaloriesBurned());
    }

    @Test
    public void testDeleteStatistics() {
        Statistics stat1 = new Statistics();
        stat1.setTotalTrainings(10);
        stat1.setTotalDistance(50.5);
        stat1.setTotalCaloriesBurned(1000);
        stat1.setAverageCaloriesBurned(100);

        when(statisticsRepository.findByUserId(1L)).thenReturn(Arrays.asList(stat1));

        statisticsProvider.deleteStatistics(1L);

        verify(statisticsRepository, times(1)).deleteAll(Arrays.asList(stat1));
}

    @Test
    public void testFindStatisticsByCaloriesBurnedGreaterThan() {
        Statistics stat1 = new Statistics();
        stat1.setTotalTrainings(10);
        stat1.setTotalDistance(50.5);
        stat1.setTotalCaloriesBurned(1500);
        stat1.setAverageCaloriesBurned(100);

        Statistics stat2 = new Statistics();
        stat2.setTotalTrainings(20);
        stat2.setTotalDistance(70.5);
        stat2.setTotalCaloriesBurned(2000);
        stat2.setAverageCaloriesBurned(200);

        when(statisticsRepository.findByTotalCaloriesBurnedGreaterThan(1000)).thenReturn(Arrays.asList(stat1, stat2));

        List<Statistics> statistics = statisticsProvider.findStatisticsByCaloriesBurnedGreaterThan(1000);
        assertEquals(2, statistics.size());
        assertEquals(1500, statistics.get(0).getTotalCaloriesBurned());
        assertEquals(2000, statistics.get(1).getTotalCaloriesBurned());
}
}
