package com.capgemini.wsb.fitnesstracker.mail.api;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class WeeklyReportGenerator {

    @Autowired
    private StatisticsProvider statisticsProvider;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 0 */14 * ?")
    public void generateWeeklyReports() {
        List<User> users = getAllUsers();
        generateWeeklyReports(users);
    }

    public void generateWeeklyReports(List<User> users) {
        LocalDate startDate = LocalDate.now().minusWeeks(2).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        for (User user : users) {
            List<Statistics> userStatistics = statisticsProvider.getStatisticsByUserId(user.getId(), startDate, endDate);
            int totalTrainings = userStatistics.size();
            System.out.println("User: " + user.getEmail() + " has " + totalTrainings + " trainings.");
            sendWeeklyReport(user, totalTrainings);
        }
    }

    private void sendWeeklyReport(User user, int totalTrainings) {
        String subject = "Weekly Training Report";
        String body = "Dear " + user.getFirstName() + ",\n\nYou have completed " + totalTrainings + " trainings in the past week.\n\nBest regards,\nFitness Tracker Team";
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    private List<User> getAllUsers() {
        return List.of();
    }
}
