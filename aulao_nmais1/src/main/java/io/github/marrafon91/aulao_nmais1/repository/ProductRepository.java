package io.github.marrafon91.aulao_nmais1.repository;

import io.github.marrafon91.aulao_nmais1.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
