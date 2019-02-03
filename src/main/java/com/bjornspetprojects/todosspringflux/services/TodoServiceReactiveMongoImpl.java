package com.bjornspetprojects.todosspringflux.services;

import com.bjornspetprojects.todosspringflux.domain.Todo;
import com.bjornspetprojects.todosspringflux.repositories.TodoRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoServiceReactiveMongoImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceReactiveMongoImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Flux<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Mono<Todo> findById(String id) {
        return todoRepository.findById(id);
    }

    @Override
    public Flux<Todo> saveAll(Publisher<Todo> todoPublisher) {
        return todoRepository.saveAll(todoPublisher);
    }

    @Override
    public Mono<Todo> save(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Mono<Todo> update(String id, Todo todo) {
        todo.setId(id);
        return todoRepository.save(todo);
    }

    @Override
    public Mono<Todo> patch(String id, Todo todo) {
        return todoRepository.findById(id).map(
                thisTodo -> {
                    thisTodo.setDescription(ServiceHelper.getPatchValue(thisTodo.getDescription(),todo.getDescription()));
                    thisTodo.setName(ServiceHelper.getPatchValue(thisTodo.getName(),todo.getName()));
                    return thisTodo;
                }
        ).flatMap(todoRepository::save);
    }
}
