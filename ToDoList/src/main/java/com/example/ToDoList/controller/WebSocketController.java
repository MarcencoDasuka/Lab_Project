package com.example.ToDoList.controller;

import com.example.ToDoList.model.ToDoItem;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/task/new")
    @SendTo("/topic/tasks")
    public ToDoItem sendNewTask(ToDoItem task) {
        return task;
    }

    @MessageMapping("/task/update")
    @SendTo("/topic/tasks")
    public ToDoItem sendUpdateTask(ToDoItem task) {
        return task;
    }

    @MessageMapping("/task/delete")
    @SendTo("/topic/tasks")
    public Integer sendDeleteTask(Integer id) {
        return id;
    }
}