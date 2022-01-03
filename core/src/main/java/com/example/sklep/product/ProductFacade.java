package com.example.sklep.product;

import com.example.sklep.product.dto.ProductDTO;
import com.example.sklep.product.dto.ProductDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ProductFacade {
    private final ProductService productService;

    public ProductFacade(ProductService productService) {
        this.productService = productService;
    }

    public Product getProductById(int productId) {
        return productService.getProductById(productId);
    }

    public Collection<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    public ProductDetailsDTO getProductDetailsByProductId(Integer productId) {
        return productService.getProductDetailsByProductId(productId);
    }
}
