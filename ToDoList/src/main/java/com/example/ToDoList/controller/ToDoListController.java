package com.example.ToDoList.controller;

import com.example.ToDoList.model.ToDoItem;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/CRUD")
public class ToDoListController {

    private List<ToDoItem> listOfItems = new ArrayList<>();
    private AtomicInteger id = new AtomicInteger();


    @PostMapping()
    public ToDoItem createItem(@RequestBody ToDoItem toDoItem){
        toDoItem.setId(id.getAndIncrement());
        listOfItems.add(toDoItem);
        return toDoItem;
    }

    @GetMapping()
    public List<ToDoItem> getAllItems (){
        return listOfItems;
    }

    @GetMapping("/{id}")
    public ToDoItem getItem (@PathVariable Integer id){
        return listOfItems.get(id);
    }

    @PutMapping("/{id}")
    public ToDoItem updateItem (@PathVariable Integer id, @RequestBody ToDoItem updatedItem){
        for (ToDoItem listOfItem : listOfItems) {
            if (listOfItem.getId().equals(id)){
                updatedItem.setId(id);
                listOfItems.set(id, updatedItem);
                return updatedItem;
            }
        }
        return null;
    }



    @DeleteMapping("/{id}")
    public void deleteItem (@PathVariable Integer id){
         listOfItems.remove(id);
    }

}
