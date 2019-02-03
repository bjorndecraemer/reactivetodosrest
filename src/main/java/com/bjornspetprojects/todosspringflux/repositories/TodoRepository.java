package com.bjornspetprojects.todosspringflux.repositories;

import com.bjornspetprojects.todosspringflux.domain.Todo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TodoRepository extends ReactiveMongoRepository<Todo,String> {
}
