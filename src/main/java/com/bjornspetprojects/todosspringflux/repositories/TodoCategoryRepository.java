package com.bjornspetprojects.todosspringflux.repositories;

import com.bjornspetprojects.todosspringflux.domain.TodoCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TodoCategoryRepository extends ReactiveMongoRepository<TodoCategory,String> {
}
