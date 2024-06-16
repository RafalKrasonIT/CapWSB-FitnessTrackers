package com.capgemini.wsb.fitnesstracker.user.internal;

import static org.mockito.Mockito.*;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailService;
import com.capgemini.wsb.fitnesstracker.mail.api.WeeklyReportGenerator;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class WeeklyReportGeneratorTest {

    @Mock
    private StatisticsProvider statisticsProvider;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private WeeklyReportGenerator weeklyReportGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateWeeklyReports() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setEmail("john.doe@example.com");

        Statistics stat1 = new Statistics();
        stat1.setTotalTrainings(1);
        stat1.setDate(LocalDate.now().minusDays(1));

        Statistics stat2 = new Statistics();
        stat2.setTotalTrainings(1);
        stat2.setDate(LocalDate.now().minusDays(2));

        when(statisticsProvider.getStatisticsByUserId(eq(user.getId()), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Arrays.asList(stat1, stat2));

        List<User> users = List.of(user);

        weeklyReportGenerator.generateWeeklyReports(users);

        String expectedBody = "Dear John,\n\nYou have completed 2 trainings in the past week.\n\nBest regards,\nFitness Tracker Team";
        verify(emailService, times(1)).sendEmail(eq(user.getEmail()), eq("Weekly Training Report"), eq(expectedBody));
    }
}