package com.bjornspetprojects.todosspringflux.services;

import com.bjornspetprojects.todosspringflux.domain.TodoCategory;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoCategoryService {
    Flux<TodoCategory> findAll();
    Mono<TodoCategory> findById(String id);
    Flux<TodoCategory> saveAll(Publisher<TodoCategory> todoCategoryPublisher);
    Mono<TodoCategory> save (TodoCategory todoCategory);
    Mono<TodoCategory> update (String id, TodoCategory todoCategory);
    Mono<TodoCategory> patch (String id, TodoCategory todoCategory);
}
