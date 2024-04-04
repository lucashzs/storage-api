package com.lucashzs.api.storage.controllers;

import com.lucashzs.api.storage.dtos.ProductDto;
import com.lucashzs.api.storage.dtos.ProductUpdateDto;
import com.lucashzs.api.storage.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid ProductDto productDto) {
        return this.productService.createProduct(productDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getQuiz(@PathVariable Long id) {
        return this.productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody ProductUpdateDto productUpdateDto, @PathVariable Long id) {
        return productService.updateProduct(productUpdateDto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return this.productService.deleteProduct(id);
    }

}
