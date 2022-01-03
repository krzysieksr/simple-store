package com.example.sklep.product.converters;

import com.example.sklep.product.Product;
import com.example.sklep.product.entites.ProductEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToProductConv implements Converter<ProductEntity, Product> {
    @Override
    public Product convert(ProductEntity source) {
        return Product.builder()
                .id(source.getProductId())
                .name(source.getName())
                .priceUSD(source.getPrice())
                .build();
    }
}
