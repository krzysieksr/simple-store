package com.krzys.warehouse.warehouse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseFacade warehouseFacade;


    public WarehouseController(WarehouseFacade warehouseFacade) {
        this.warehouseFacade = warehouseFacade;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Flux<Integer>> getProductAmount(@PathVariable Integer productId) {
        Flux<Integer> productAmount = warehouseFacade.getProductAmount(productId);
        return ResponseEntity.ok().body(productAmount);
//        return warehouseFacade.getProductAmount(productId)
//                .map(e -> ResponseEntity.ok(e))
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
