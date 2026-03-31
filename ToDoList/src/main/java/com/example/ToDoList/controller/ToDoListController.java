package com.example.ToDoList.controller;

import com.example.ToDoList.model.ToDoItem;
import com.example.ToDoList.repository.ToDoListRepo;
import com.example.ToDoList.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/CRUD")
@CrossOrigin(origins = "*")
public class ToDoListController {

    @Autowired
    private ToDoListRepo repository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ToDoItem createItem(@RequestBody ToDoItem item) {
        return repository.save(item);
    }

    @GetMapping
    public List<ToDoItem> getAllItems() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ToDoItem getItem(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ToDoItem updateItem(@PathVariable Integer id, @RequestBody ToDoItem updated) {
        ToDoItem existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.getCompleted());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @PostMapping("/{id}/send-email")
    public ResponseEntity<?> sendTaskByEmail(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email не указан"));
        }

        ToDoItem task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        try {
            emailService.sendTaskByEmail(email, task);
            return ResponseEntity.ok(Map.of("message", "Письмо отправлено на " + email));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Ошибка отправки: " + e.getMessage()));
        }
    }
}