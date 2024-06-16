package com.capgemini.wsb.fitnesstracker.statistics.api;

public class StatisticsDto {
    private int totalTrainings;
    private double totalDistance;
    private int totalCaloriesBurned;
    private double averageCaloriesBurned;


    public int getTotalTrainings() {
        return totalTrainings;
    }

    public void setTotalTrainings(int totalTrainings) {
        this.totalTrainings = totalTrainings;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(int totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public double getAverageCaloriesBurned() {
        return averageCaloriesBurned;
    }

    public void setAverageCaloriesBurned(double averageCaloriesBurned) {
        this.averageCaloriesBurned = averageCaloriesBurned;
    }
}
