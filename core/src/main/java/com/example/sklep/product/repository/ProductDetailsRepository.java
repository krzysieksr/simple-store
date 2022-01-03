package com.example.sklep.product.repository;

import com.example.sklep.product.entites.ProductDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailsRepository extends JpaRepository<ProductDetailsEntity, Integer> {
}
