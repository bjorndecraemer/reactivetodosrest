package com.bjornspetprojects.todosspringflux.services;

import com.bjornspetprojects.todosspringflux.domain.TodoCategory;
import com.bjornspetprojects.todosspringflux.repositories.TodoCategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoCategoryServiceReactiveMongoImpl implements TodoCategoryService {

    private final TodoCategoryRepository todoCategoryRepository;

    public TodoCategoryServiceReactiveMongoImpl(TodoCategoryRepository todoCategoryRepository) {
        this.todoCategoryRepository = todoCategoryRepository;
    }

    @Override
    public Flux<TodoCategory> findAll() {
        return todoCategoryRepository.findAll();
    }

    @Override
    public Mono<TodoCategory> findById(String id) {
        return todoCategoryRepository.findById(id);
    }

    @Override
    public Flux<TodoCategory> saveAll(Publisher<TodoCategory> todoCategoryPublisher) {
        return todoCategoryRepository.saveAll(todoCategoryPublisher);
    }

    @Override
    public Mono<TodoCategory> save(TodoCategory todoCategory) {
        return todoCategoryRepository.save(todoCategory);
    }

    @Override
    public Mono<TodoCategory> update(String id, TodoCategory todoCategory) {
        todoCategory.setId(id);
        return todoCategoryRepository.save(todoCategory);
    }

    @Override
    public Mono<TodoCategory> patch(String id, TodoCategory todoCategory) {
        return todoCategoryRepository.findById(id).map(
                existingCategory -> {
                        todoCategory.setDescription(ServiceHelper.getPatchValue(existingCategory.getDescription(), todoCategory.getDescription()));
                        return todoCategory;
                })
                .flatMap(todoCategoryRepository::save);
    }
}
