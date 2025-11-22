package io.github.marrafon91.dscommerce.repositories;

import io.github.marrafon91.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
