package com.example.sklep.product.entites;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ProductDetails")
public class ProductDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int productDetailsId;

    private String description;

    private String imagesPaths;

    @OneToOne(mappedBy = "productDetailsEntity", fetch = FetchType.LAZY)
    private ProductEntity productEntity;
}
