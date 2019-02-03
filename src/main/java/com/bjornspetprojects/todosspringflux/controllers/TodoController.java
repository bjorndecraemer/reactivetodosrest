package com.bjornspetprojects.todosspringflux.controllers;

import com.bjornspetprojects.todosspringflux.domain.Todo;
import com.bjornspetprojects.todosspringflux.services.TodoService;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public Flux<Todo> findAll(){
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Todo> findById(@PathVariable String id){
        return todoService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Flux<Todo> create(@RequestBody Publisher<Todo> todoStream){
        return todoService.saveAll(todoStream);
    }

    @PutMapping("/{id}")
    public Mono<Todo> update(@PathVariable String id, @RequestBody Todo todo){
        return todoService.update(id,todo);
    }
    @PatchMapping("/{id}")
    public Mono<Todo> patch(@PathVariable String id, @RequestBody Todo todo){
        todo.setId(id);
        return todoService.patch(id,todo);
    }

}
