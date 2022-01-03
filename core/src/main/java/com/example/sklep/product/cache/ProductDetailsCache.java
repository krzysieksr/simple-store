package com.example.sklep.product.cache;

import com.example.common.ProductNotFoundException;
import com.example.sklep.product.ProductDetails;
import com.example.sklep.product.converters.ProductDetailsEntityToProductDetailsConv;
import com.example.sklep.product.entites.ProductEntity;
import com.example.sklep.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProductDetailsCache {
    private final EvictPolicy<Integer, ProductDetails> evictPolicy;
    private final Map<Integer, ProductDetails> memCache;
    private final ProductRepository productRepository;
    private final ProductDetailsEntityToProductDetailsConv converter;
    @Value("${productDetailsCache.boundSize:10}")
    private int boundSize;

    public ProductDetailsCache(EvictPolicy<Integer, ProductDetails> evictPolicy,
                               ProductRepository productRepository,
                               ProductDetailsEntityToProductDetailsConv converter) {
        this.evictPolicy = evictPolicy;
        this.productRepository = productRepository;
        this.converter = converter;
        this.memCache = new ConcurrentHashMap<>();
    }

    @Transactional
    public ProductDetails get(Integer productId) {
        if (memCache.containsKey(productId)) {
            evictPolicy.updateTime(productId);
            return memCache.get(productId);
        }

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        ProductNotFoundException.PRODUCT_MESSAGE, productId));
        ProductDetails productDetails = converter.convert(productEntity.getProductDetailsEntity());

        put(productId, productDetails);

        return productDetails;
    }

    private synchronized void put(Integer productId, ProductDetails productDetails) {
        if (memCache.size() >= boundSize) {
            Integer evictProductId = evictPolicy.evict();
            remove(evictProductId);
        }

        evictPolicy.put(productId, productDetails);
        memCache.put(productId, productDetails);

    }

    private synchronized void remove(Integer productId) {
        memCache.remove(productId);
    }
}
