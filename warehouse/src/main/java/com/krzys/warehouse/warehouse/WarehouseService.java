package com.krzys.warehouse.warehouse;

import com.example.common.ProductNotFoundException;
import com.example.common.TooFewProductAvailableException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Flux<Integer> checkProductAmount(int productId) throws ProductNotFoundException {
        return warehouseRepository.findByProductId(productId)
                .map(product -> {
                    return product.getAmount();
                });
//                TODO what if productId doesn't exist
//                .orElseThrow(() -> new ProductNotFoundException(ProductNotFoundException.WAREHOUSE_MESSAGE, productId))
//                .getAmount();
    }

    public void takeProducts(Integer productId, Integer amount) {
        Flux<WarehouseEntity> byProductId = warehouseRepository.findByProductId(productId);
        WarehouseEntity warehouseEntity = byProductId.blockFirst();
        Integer availableAmount = warehouseEntity.getAmount();
        int newAmount = availableAmount - amount;
        if (newAmount < 0) {
            throw new TooFewProductAvailableException(availableAmount, amount);
        }
        warehouseEntity.setAmount(newAmount);
        warehouseRepository.save(warehouseEntity).subscribe();
    }
}
