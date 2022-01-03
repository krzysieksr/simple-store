package com.example.sklep.product;

import com.example.common.ProductNotFoundException;
import com.example.sklep.product.cache.ProductDetailsCache;
import com.example.sklep.product.converters.ProductDetailsToProductDetailsDtoConv;
import com.example.sklep.product.converters.ProductEntityToProductConv;
import com.example.sklep.product.converters.ProductEntityToProductDtoConv;
import com.example.sklep.product.dto.ProductDTO;
import com.example.sklep.product.dto.ProductDetailsDTO;
import com.example.sklep.product.entites.ProductEntity;
import com.example.sklep.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailsCache productDetailsCache;
    private final ProductEntityToProductDtoConv productEntityToProductDtoConv;
    private final ProductDetailsToProductDetailsDtoConv productDetailsToProductDetailsDtoConv;


    public ProductService(ProductRepository productRepository, ProductDetailsCache productDetailsCache, ProductEntityToProductConv productEntityToProductConv, ProductEntityToProductDtoConv productEntityToProductDtoConv, ProductDetailsToProductDetailsDtoConv productDetailsToProductDetailsDtoConv) {
        this.productRepository = productRepository;
        this.productDetailsCache = productDetailsCache;
        this.productEntityToProductDtoConv = productEntityToProductDtoConv;
        this.productDetailsToProductDetailsDtoConv = productDetailsToProductDetailsDtoConv;
    }

    @Transactional
    public Product getProductById(int productId) {
        ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ProductNotFoundException.PRODUCT_MESSAGE, productId));

        return Product.builder()
                .id(productEntity.getProductId())
                .name(productEntity.getName())
                .priceUSD(productEntity.getPrice())
                .build();
    }

    //    TODO can add Pagination feature
    @Transactional
    public Collection<ProductDTO> getAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        return productEntityList.stream().map(
                        productEntity -> productEntityToProductDtoConv.convert(productEntity))
                .collect(Collectors.toList());
    }

    public ProductDetailsDTO getProductDetailsByProductId(Integer productId) {
        ProductDetails productDetails = productDetailsCache.get(productId);
        return productDetailsToProductDetailsDtoConv.convert(productDetails);
    }
}
