package io.github.marrafon91.aulao_nmais1.dto;

import io.github.marrafon91.aulao_nmais1.entities.Category;

public record CategoryDTO(Long id, String name) {
    public CategoryDTO(Category category) {
        this (
                category.getId(),
                category.getName()
        );
    }
}
