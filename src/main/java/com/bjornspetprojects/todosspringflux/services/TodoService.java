package com.bjornspetprojects.todosspringflux.services;

import com.bjornspetprojects.todosspringflux.domain.Todo;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoService {
    Flux<Todo> findAll();
    Mono<Todo> findById(String id);
    Flux<Todo> saveAll(Publisher<Todo> todoPublisher);
    Mono<Todo> save(Todo todo);
    Mono<Todo> update(String id, Todo todo);
    Mono<Todo> patch(String id, Todo todo);
}
