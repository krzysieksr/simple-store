package com.example.sklep.product;

import com.example.sklep.product.dto.ProductDTO;
import com.example.sklep.product.dto.ProductDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {
    private final ProductFacade productFacade;

    @GetMapping
    public Collection<ProductDTO> findAll() {
        return productFacade.getAllProducts();
    }

    @GetMapping("/details/{productId}")
    public ProductDetailsDTO getProductDetails(@PathVariable Integer productId) {
        return productFacade.getProductDetailsByProductId(productId);
    }
}
