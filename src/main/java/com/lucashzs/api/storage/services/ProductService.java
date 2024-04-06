package com.lucashzs.api.storage.services;

import com.lucashzs.api.storage.dtos.AddAmountDto;
import com.lucashzs.api.storage.dtos.ProductDto;
import com.lucashzs.api.storage.dtos.ProductUpdateDto;
import com.lucashzs.api.storage.entities.Product;
import com.lucashzs.api.storage.errors.exceptions.NotFoundException;
import com.lucashzs.api.storage.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

    public ResponseEntity<ProductDto> getProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product Not Found!"));
        ProductDto productDto = new ProductDto(product.getName(), product.getSector(), product.getAmount());

        return ResponseEntity.ok(productDto);
    }

    public ResponseEntity<?> addAmount(Long id, AddAmountDto amountDto) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found!");
        }
        product.setAmount(product.getAmount() + amountDto.getAmount());
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added Amount Successfully!");
    }

    @Transactional
    public ResponseEntity<Object> updateProduct(ProductUpdateDto productDto, Long id) {
        Product product = findById(id);

        product.setName(productDto.name());
        product.setSector(productDto.sector());

        this.productRepository.save(product);
        return ResponseEntity.status(HttpStatus.OK).body("Update Successfully");
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
