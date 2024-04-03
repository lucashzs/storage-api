package com.lucashzs.api.storage.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDto(
        @NotBlank(message = "You need a name to add the product!") String name,
        @NotBlank(message = "You need a quantity to add the item!") String sector,
        @NotNull(message = "You need a sector to add the product!") Long amount) {
}
