package com.lucashzs.api.storage.dtos;

import com.lucashzs.api.storage.entities.Product;

public record ProductUpdateDto(String name, String sector) {
    public ProductUpdateDto (Product product){
        this(product.getName(), product.getSector());
    }
}
