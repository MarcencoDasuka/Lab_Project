package com.example.ToDoAnalytics.controller;

import com.example.ToDoAnalytics.model.AnalyticsData;
import com.example.ToDoAnalytics.model.ToDoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ToDoAnalytics.service.AnalyticsService;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/stats")
    public AnalyticsData getStats() {
        return analyticsService.getAnalytics();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Analytics Service работает";
    }

    @GetMapping("/test")
    public String test() {
            AnalyticsData data = analyticsService.getAnalytics();
            return "Получено задач: " + data.getTotalTasks();
    }

    @PostMapping("/mark-completed/{taskId}")
    public ResponseEntity<?> markCompleted(@PathVariable Integer taskId) {
            ToDoItem updatedTask = analyticsService.markTaskAsCompleted(taskId);
            AnalyticsData newStats = analyticsService.getAnalytics();
            return ResponseEntity.ok(newStats);

    }
}