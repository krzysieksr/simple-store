package com.example.sklep.product.converters;

import com.example.sklep.product.ProductDetails;
import com.example.sklep.product.dto.ProductDetailsDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailsToProductDetailsDtoConv implements Converter<ProductDetails, ProductDetailsDTO> {
    @Override
    public ProductDetailsDTO convert(ProductDetails source) {
        return ProductDetailsDTO.builder()
                .id(source.getId())
                .description(source.getDescription())
                .imagesPaths(source.getImagesPaths())
                .build();
    }
}
