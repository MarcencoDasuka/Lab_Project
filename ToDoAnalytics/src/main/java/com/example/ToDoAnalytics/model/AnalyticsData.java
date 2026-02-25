package com.example.ToDoAnalytics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsData {
    private Integer totalTasks;
    private Integer completedTasks;

}