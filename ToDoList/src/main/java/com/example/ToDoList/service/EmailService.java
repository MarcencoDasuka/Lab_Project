package com.example.ToDoList.service;

import com.example.ToDoList.model.ToDoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.recipient}")
    private String defaultRecipient;

    public void sendTaskByEmail(String recipientEmail, ToDoItem task) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Задача из ToDo List: " + task.getTitle());

        String emailBody = String.format(
                "Здравствуйте\n\n" +
                        "Вам отправлена задача из ToDo List'а:\n\n" +
                        "ID: %d\n" +
                        "Название: %s\n" +
                        "Описание: %s\n" +
                        "Статус: %s\n\n" +
                        "С уважением,\nToDo List Manager",
                task.getId(),
                task.getTitle(),
                task.getDescription() != null ? task.getDescription() : "Нет описания",
                task.getCompleted() ? "Выполнена" : "Не выполнена"
        );

        message.setText(emailBody);
        mailSender.send(message);
    }
}