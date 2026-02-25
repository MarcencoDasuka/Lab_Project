package com.example.ToDoList.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoItem {
    private Integer id;
    private String title;
    private String description;
    private Boolean completed;
}