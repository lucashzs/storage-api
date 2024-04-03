package com.lucashzs.api.storage.services;

import com.lucashzs.api.storage.dtos.ProductDto;
import com.lucashzs.api.storage.entities.Product;
import com.lucashzs.api.storage.errors.exceptions.NotFoundException;
import com.lucashzs.api.storage.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AuthenticationService authenticationService;

    public ProductService(ProductRepository productRepository, AuthenticationService authenticationService) {
        this.productRepository = productRepository;
        this.authenticationService = authenticationService;
    }

    public Product findById(Long id) {
        return this.productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product Not Found ID: " + id));
    }

    public ResponseEntity<Object> createProduct(ProductDto productDto) {
        var currentUser = authenticationService.getCurrentUser();

        var newProduct = new Product(productDto);
        if (this.productRepository.findByName(productDto.name()).isPresent()) {
            throw new NotFoundException("Quiz already exists!");
        }

        newProduct.setUser(currentUser);

        this.productRepository.save(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Product Successfully!");
    }





    public ResponseEntity<Object> deleteProduct(Long id) {
        findById(id);
        try {
            this.productRepository.deleteById(id);
        } catch (Exception ex) {
            throw new NotFoundException("Product Not Found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Delete Product Successfully ID: %s", id));
    }
}
