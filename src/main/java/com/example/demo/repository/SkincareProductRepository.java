package com.example.demo.repository;

import com.example.demo.model.SkincareProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkincareProductRepository extends MongoRepository<SkincareProduct, String> {
}
