package com.bjornspetprojects.todosspringflux.controllers;

import com.bjornspetprojects.todosspringflux.domain.TodoCategory;
import com.bjornspetprojects.todosspringflux.repositories.TodoCategoryRepository;
import com.bjornspetprojects.todosspringflux.services.TodoCategoryService;
import com.bjornspetprojects.todosspringflux.services.TodoCategoryServiceReactiveMongoImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

public class TodoCategoryControllerTest {

    private WebTestClient webTestClient;
    private TodoCategoryRepository todoCategoryRepository;
    private TodoCategoryService todoCategoryService;
    private TodoCategoryController todoCategoryController;

    @Before
    public void setUp(){
        todoCategoryRepository = Mockito.mock(TodoCategoryRepository.class);
        todoCategoryService = new TodoCategoryServiceReactiveMongoImpl(todoCategoryRepository);
        todoCategoryController = new TodoCategoryController(todoCategoryService);
        webTestClient = WebTestClient.bindToController(todoCategoryController).build();
    }

    @Test
    public void findAllTodoCategories() {
        BDDMockito.given(todoCategoryRepository.findAll())
                .willReturn(Flux.just(
                        TodoCategory.builder().description("Cat1Desc").build(),
                        TodoCategory.builder().description("Cat2Desc").build()
                        ));
        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(TodoCategory.class)
                .hasSize(2);
    }

    @Test
    public void findById() {
        BDDMockito.given(todoCategoryRepository.findById("someId"))
                .willReturn(Mono.just(
                        TodoCategory.builder().description("Cat1Desc").id("someId").build()
                ));
        webTestClient.get().uri("/api/v1/categories/someId")
                .exchange()
                .expectBody(TodoCategory.class)
                .isEqualTo(TodoCategory.builder().description("Cat1Desc").id("someId").build());
    }

    @Test
    public void create() {
        BDDMockito.given(todoCategoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(TodoCategory.builder().build()));

        Mono<TodoCategory> catToSaveMono = Mono.just(TodoCategory.builder().description("someDescription").build());

        webTestClient.post().uri("/api/v1/categories")
                .body(catToSaveMono,TodoCategory.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void update() {
        Mono<TodoCategory> returnTodoCategory = Mono.just(TodoCategory.builder().id("someId").build());
        BDDMockito.given(todoCategoryRepository.save(any(TodoCategory.class)))
                .willReturn(returnTodoCategory);

        Mono<TodoCategory> catToUpdateMono = Mono.just(TodoCategory.builder().description("someNewDescription").build());

        webTestClient.put().uri("/api/v1/categories/someId")
                .body(catToUpdateMono, TodoCategory.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TodoCategory.class)
                .isEqualTo(returnTodoCategory.block());
    }

    @Test
    public void patch() {
        Mono<TodoCategory> returnTodoCategory = Mono.just(TodoCategory.builder().id("someId").build());
        BDDMockito.given(todoCategoryRepository.save(any(TodoCategory.class)))
                .willReturn(returnTodoCategory);

        Mono<TodoCategory> catToUpdateMono = Mono.just(TodoCategory.builder().description("someNewDescription").build());

        webTestClient.patch().uri("/api/v1/categories/someId")
                .body(catToUpdateMono, TodoCategory.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TodoCategory.class)
                .isEqualTo(returnTodoCategory.block());
    }
}