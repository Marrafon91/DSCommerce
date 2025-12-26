package io.github.marrafon91.dscommerce.repositories;

import io.github.marrafon91.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
