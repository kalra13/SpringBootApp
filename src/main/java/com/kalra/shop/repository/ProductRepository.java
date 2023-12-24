package com.kalra.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kalra.shop.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>
{
}
