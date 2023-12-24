package com.kalra.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kalra.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>
{
}
