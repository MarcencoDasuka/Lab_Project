package com.example.ToDoList.controller;

import com.example.ToDoList.model.ToDoItem;
import com.example.ToDoList.repository.ToDoListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CRUD")
@CrossOrigin(origins = "*")
public class ToDoListController {

    @Autowired
    private ToDoListRepo repository;

    @PostMapping
    public ToDoItem createItem(@RequestBody ToDoItem item) {
        item.setId(null);
        return repository.save(item);
    }

    @GetMapping
    public List<ToDoItem> getAllItems() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ToDoItem getItem(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new RuntimeException("Task not found"));
    }

    @PutMapping("/{id}")
    public ToDoItem updateItem(@PathVariable Integer id, @RequestBody ToDoItem updated) {
        ToDoItem existing = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Task not found"));
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.getCompleted());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}