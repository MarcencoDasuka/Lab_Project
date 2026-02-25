package com.example.ToDoAnalytics.service;

import com.example.ToDoAnalytics.model.AnalyticsData;
import com.example.ToDoAnalytics.model.ToDoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${todo.service.url:http://todolist-container:8081/CRUD}")
    private String todoServiceBaseUrl;

    public List<ToDoItem> getTasksFromTodoService() {
        ResponseEntity<ToDoItem[]> response = restTemplate.getForEntity(
                todoServiceBaseUrl,
                ToDoItem[].class
        );
        return Arrays.asList(response.getBody());


    }

    public AnalyticsData calculateStats(List<ToDoItem> tasks) {
        if (tasks.isEmpty()) {
            return new AnalyticsData(0, 0);
        }

        long completedCount = tasks.stream()
                .filter(task -> Boolean.TRUE.equals(task.getCompleted()))
                .count();

        return new AnalyticsData(tasks.size(), (int) completedCount);
    }

    public ToDoItem markTaskAsCompleted(Integer taskId) {
        String taskUrl = todoServiceBaseUrl + "/" + taskId;
        ResponseEntity<ToDoItem> getResponse = restTemplate.getForEntity(taskUrl, ToDoItem.class);

        if (getResponse.getStatusCode().is2xxSuccessful() && getResponse.getBody() != null) {
            ToDoItem current = getResponse.getBody();
            current.setCompleted(true);

            HttpEntity<ToDoItem> request = new HttpEntity<>(current);
            ResponseEntity<ToDoItem> putResponse = restTemplate.exchange(
                    taskUrl,
                    HttpMethod.PUT,
                    request,
                    ToDoItem.class
            );
            if (putResponse.getStatusCode().is2xxSuccessful()) {
                return putResponse.getBody();
            } else {
                throw new RuntimeException("Не удалось обновить задачу: " + putResponse.getStatusCode());
            }
        } else {
            throw new RuntimeException("Задача с id " + taskId + " не найдена");
        }
    }

    public AnalyticsData getAnalytics() {
        List<ToDoItem> tasks = getTasksFromTodoService();
        AnalyticsData stats = calculateStats(tasks);
        return stats;
    }
}