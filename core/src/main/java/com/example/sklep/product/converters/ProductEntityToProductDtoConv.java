package com.example.sklep.product.converters;

import com.example.sklep.product.dto.ProductDTO;
import com.example.sklep.product.entites.ProductEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToProductDtoConv implements Converter<ProductEntity, ProductDTO> {
    @Override
    public ProductDTO convert(ProductEntity source) {
        return ProductDTO.builder()
                .id(source.getProductId())
                .name(source.getName())
                .priceUSD(source.getPrice())
                .build();
    }
}
