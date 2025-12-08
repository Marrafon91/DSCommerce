package io.github.marrafon91.aulao_nmais1.dto;

import io.github.marrafon91.aulao_nmais1.entities.Product;

public record ProductDTO(Long id, String name) {
    public ProductDTO(Product product) {
        this (
                product.getId(),
                product.getName()
        );
    }
}
