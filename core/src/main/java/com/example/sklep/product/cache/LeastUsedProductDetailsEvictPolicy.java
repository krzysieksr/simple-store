package com.example.sklep.product.cache;

import com.example.sklep.product.ProductDetails;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LeastUsedProductDetailsEvictPolicy implements EvictPolicy<Integer, ProductDetails> {
    // key productId, value time of recent usage
    private Map<Integer, Long> timeOfUse;

    public LeastUsedProductDetailsEvictPolicy() {
        this.timeOfUse = new ConcurrentHashMap<>();
    }

    @Override
    public Integer evict() {
        if (timeOfUse.isEmpty()) {
            return null;
        }

        Integer leastUsedProductId = timeOfUse.entrySet()
                .stream()
                .min((o1, o2) -> o1.getValue().compareTo(o2.getValue())).get().getKey();

        timeOfUse.remove(leastUsedProductId);
        return leastUsedProductId;
    }

    @Override
    public void put(Integer key, ProductDetails value) {
        timeOfUse.put(key, System.nanoTime());
    }

    @Override
    public void updateTime(Integer key) {
        timeOfUse.replace(key, System.nanoTime());
    }
}
