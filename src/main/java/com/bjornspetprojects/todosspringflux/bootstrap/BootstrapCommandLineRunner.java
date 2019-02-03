package com.bjornspetprojects.todosspringflux.bootstrap;

import com.bjornspetprojects.todosspringflux.domain.Todo;
import com.bjornspetprojects.todosspringflux.domain.TodoCategory;
import com.bjornspetprojects.todosspringflux.repositories.TodoCategoryRepository;
import com.bjornspetprojects.todosspringflux.repositories.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapCommandLineRunner implements CommandLineRunner {

    private final TodoCategoryRepository todoCategoryRepository;
    private final TodoRepository todoRepository;

    public BootstrapCommandLineRunner(TodoCategoryRepository todoCategoryRepository, TodoRepository todoRepository) {
        this.todoCategoryRepository = todoCategoryRepository;
        this.todoRepository = todoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //todoRepository.deleteAll().block();
        //todoCategoryRepository.deleteAll().block();
        if(todoCategoryRepository.count().block() == 0){
            System.out.print("*** Loading Categories ...");
            TodoCategory todoCategory1 = new TodoCategory();
            todoCategory1.setDescription("Leasure");
            TodoCategory todoCategory2 = new TodoCategory();
            todoCategory2.setDescription("Work");
            TodoCategory todoCategory3 = new TodoCategory();
            todoCategory3.setDescription("Household");
            todoCategory1 = todoCategoryRepository.save(todoCategory1).block();
            todoCategory2 = todoCategoryRepository.save(todoCategory2).block();
            todoCategory3 = todoCategoryRepository.save(todoCategory3).block();
            System.out.print(" ("+todoCategoryRepository.count().block()+")");
            System.out.println(" done!");
            System.out.print("Loading Todo's ... ");
            todoRepository.save(Todo.builder().name("Go for walk").description("Go for a walk").todoCategory(todoCategory1).build()).block();
            todoRepository.save(Todo.builder().name("Do dishes").description("Do the dishes that are dirty").todoCategory(todoCategory3).build()).block();
            todoRepository.save(Todo.builder().name("Finish webservice code").description("Finish the microservices webszervices!").todoCategory(todoCategory2).build()).block();
            todoRepository.save(Todo.builder().name("Prepare dinner").description("Prepare a tasty Lasagna").todoCategory(todoCategory3).build()).block();
            System.out.print("("+todoRepository.count().block()+") ");
            System.out.println(" done!");
        }


        if(todoRepository.count().block() == 0){

        }

    }
}
