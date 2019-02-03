package com.bjornspetprojects.todosspringflux.controllers;

import com.bjornspetprojects.todosspringflux.domain.TodoCategory;
import com.bjornspetprojects.todosspringflux.services.TodoCategoryService;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/categories")
public class TodoCategoryController {
    private final TodoCategoryService todoCategoryService;

    public TodoCategoryController(TodoCategoryService todoCategoryService) {
        this.todoCategoryService = todoCategoryService;
    }

    @GetMapping
    public Flux<TodoCategory> findAllTodoCategories (){
        return todoCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<TodoCategory> findById (@PathVariable String id){
        return todoCategoryService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Flux<TodoCategory> create (@RequestBody Publisher<TodoCategory> todoCategoryStream){
        return todoCategoryService.saveAll(todoCategoryStream);
    }

    @PutMapping("/{id}")
    public Mono<TodoCategory> update(@PathVariable String id, @RequestBody TodoCategory todoCategory){
        todoCategory.setId(id);
        return todoCategoryService.save(todoCategory);
    }
    @PatchMapping("/{id}")
    public Mono<TodoCategory> patch(@PathVariable String id, @RequestBody TodoCategory todoCategory){
        todoCategory.setId(id);
        return todoCategoryService.save(todoCategory);
    }
}
