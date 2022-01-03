package com.krzys.warehouse;

import com.krzys.warehouse.warehouse.WarehouseEntity;
import com.krzys.warehouse.warehouse.WarehouseRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class DbInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final WarehouseRepository warehouseRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public DbInitializer(WarehouseRepository warehouseRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.warehouseRepository = warehouseRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createWarehouseEntities();
    }


    public void createWarehouseEntities() {
        WarehouseEntity warehouseEntity1 = WarehouseEntity.builder()
                .productId(1)
                .amount(30)
                .build();
        WarehouseEntity warehouseEntity2 = WarehouseEntity.builder()
                .productId(2)
                .amount(25)
                .build();
        WarehouseEntity warehouseEntity3 = WarehouseEntity.builder()
                .productId(3)
                .amount(15)
                .build();
        WarehouseEntity warehouseEntity4 = WarehouseEntity.builder()
                .productId(4)
                .amount(17)
                .build();
        WarehouseEntity warehouseEntity5 = WarehouseEntity.builder()
                .productId(5)
                .amount(2)
                .build();

        warehouseRepository.saveAll(Flux.just(warehouseEntity1, warehouseEntity2,
                warehouseEntity3, warehouseEntity4, warehouseEntity5)).subscribe();
    }
}
