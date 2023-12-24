package com.kalra.shop.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.kalra.shop.dto.ProductRequest;
import com.kalra.shop.model.Product;
import com.kalra.shop.repository.ProductRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductControllerTest
{
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry)
    {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testCreateProduct() throws Exception
    {
        ProductRequest productRequest = getProductRequest();
        ObjectMapper mapper = new ObjectMapper();

        String requestBody = mapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        Assertions.assertEquals(1, productRepository.findAll().size());
    }


    @Test
    void testGetProduct() throws Exception
    {
        Product product = new Product("13", "Apple", "This is to test", BigDecimal.valueOf(1314));
        productRepository.save(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/get")).andExpect(status().isOk());

        Optional<Product> byId = productRepository.findById("13");

        Assertions.assertEquals(product, byId.get());

    }

    private ProductRequest getProductRequest()
    {
        return ProductRequest.builder()
                .name("Samsung-Ultra")
                .description("Flip-flap: 512 GB")
                .price(BigDecimal.valueOf(1500))
                .build();
    }
}