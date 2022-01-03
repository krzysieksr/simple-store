package com.krzys.warehouse.warehouse;

import com.example.common.OrderKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class WarehouseFacade {

    private final WarehouseService warehouseService;

    public WarehouseFacade(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    public Flux<Integer> getProductAmount(Integer productId) {
        //TODO how about ProductNotFoundException??
        return warehouseService.checkProductAmount(productId);
    }

    @KafkaListener(topics = "warehouse", groupId = "warehouse")
    public void consume(OrderKafka orderKafka) {
        orderKafka.getProductIdToNumberMap().forEach(
                (key, value) -> warehouseService.takeProducts(key, value));
    }
}
