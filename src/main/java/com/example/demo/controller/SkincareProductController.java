package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.SkincareProduct;
import com.example.demo.repository.SkincareProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skincare")
public class SkincareProductController {

    @Autowired
    private SkincareProductRepository repository;

    @GetMapping
    public ResponseEntity<List<SkincareProduct>> getAllSkincareProducts() {
        try {
            List<SkincareProduct> products = new ArrayList<>(repository.findAll());
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkincareProductById(@PathVariable String id) {
        Optional<SkincareProduct> productData = repository.findById(id);
        if (productData.isPresent()) {
            return new ResponseEntity<>(productData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Skincare product not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<SkincareProduct> createSkincareProduct(@RequestBody SkincareProduct product) {
        try {
            SkincareProduct savedProduct = repository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkincareProduct(@PathVariable String id, @RequestBody SkincareProduct product) {
        Optional<SkincareProduct> productData = repository.findById(id);
        if (productData.isPresent()) {
            SkincareProduct _product = productData.get();
            _product.setName(product.getName());
            _product.setBrand(product.getBrand());
            _product.setPrice(product.getPrice());
            _product.setDescription(product.getDescription());
            return new ResponseEntity<>(repository.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Skincare product not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSkincareProduct(@PathVariable String id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllSkincareProducts() {
        try {
            repository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
