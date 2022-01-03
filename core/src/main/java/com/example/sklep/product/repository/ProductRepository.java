package com.example.sklep.product.repository;

import com.example.sklep.product.entites.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}
