package com.bjornspetprojects.todosspringflux.controllers;

import com.bjornspetprojects.todosspringflux.domain.Todo;
import com.bjornspetprojects.todosspringflux.repositories.TodoRepository;
import com.bjornspetprojects.todosspringflux.services.TodoService;
import com.bjornspetprojects.todosspringflux.services.TodoServiceReactiveMongoImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
public class TodoControllerTest {

    private TodoRepository todoRepository;
    private TodoService todoService;
    private TodoController todoController;
    private WebTestClient webTestClient;

    @Before
    public void setUp(){
        todoRepository = mock(TodoRepository.class);
        todoService = new TodoServiceReactiveMongoImpl(todoRepository);
        todoController = new TodoController((todoService));
        webTestClient = WebTestClient.bindToController(todoController).build();
    }

    @Test
    public void findAll() {
        BDDMockito.when(todoRepository.findAll())
                .thenReturn(Flux.just(
                        Todo.builder().description("todo1Desc").name("todo1Name").build(),
                        Todo.builder().description("todo2Desc").name("todo2Name").build()
                ));

        webTestClient.get().uri("/api/v1/todos")
                .exchange()
                .expectBodyList(Todo.class)
                .hasSize(2);
    }

    @Test
    public void findById() {
        BDDMockito.given(todoRepository.findById("someString"))
                .willReturn(Mono.just(Todo.builder().id("someString").build()));
        webTestClient.get().uri("/api/v1/todos/someString")
                .exchange()
                .expectBody(Todo.class)
                .isEqualTo(Todo.builder().id("someString").build());
    }

    @Test
    public void create() {
        BDDMockito.given(todoRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Todo.builder().build()));

        webTestClient.post().uri("/api/v1/todos")
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void update() {
        Mono<Todo> returnMono = Mono.just(Todo.builder().id("someId").build());
        BDDMockito.given(todoRepository.save(any(Todo.class)))
                .willReturn(returnMono);

        Mono<Todo> toUpdateMono = Mono.just(Todo.builder().id("someId").build());
        webTestClient.put().uri("/api/v1/todos/someId")
                .body(toUpdateMono,Todo.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Todo.class)
                .isEqualTo(returnMono.block());
    }
}