package com.krzys.warehouse.warehouse;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WarehouseRepository extends ReactiveMongoRepository<WarehouseEntity, Integer> {
    Flux<WarehouseEntity> findByProductId(int productId);
}
