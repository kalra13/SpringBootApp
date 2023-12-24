package com.kalra.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kalra.productservice.dto.ProductRequest;
import com.kalra.productservice.dto.ProductResponse;
import com.kalra.productservice.model.Product;
import com.kalra.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService
{

    private final ProductRepository productRepository;


    public void createProduct(ProductRequest productRequest)
    {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts()
    {
       return productRepository.findAll()
                .stream()
                .map(e -> new ProductResponse(e.getId(), e.getName(), e.getDescription(), e.getPrice()))
                .toList();


    }
}
