package io.github.marrafon91.aulao_nmais1.dto;

import io.github.marrafon91.aulao_nmais1.entities.Product;

import java.util.List;

public record ProductDTO(Long id, String name, List<CategoryDTO> categories) {
    public ProductDTO(Product product) {
        this (
                product.getId(),
                product.getName(),
                product.getCategories().stream().map(CategoryDTO::new).toList()
        );
    }
}
