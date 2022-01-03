package com.krzys.warehouse;

import com.krzys.warehouse.warehouse.WarehouseEntity;
import org.springframework.context.Lifecycle;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLifecycle implements Lifecycle {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public ApplicationLifecycle(ReactiveMongoTemplate reactiveMongoTemplate, ReactiveMongoOperations reactiveMongoOperations) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
//        TODO drop DB
        reactiveMongoTemplate.dropCollection(WarehouseEntity.class).subscribe();
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
