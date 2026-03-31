package com.example.ToDoList.repository;

import com.example.ToDoList.model.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListRepo extends JpaRepository<ToDoItem, Integer> {
}