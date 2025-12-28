package io.github.marrafon91.dscommerce.repositories;

import io.github.marrafon91.dscommerce.entities.OrderItem;
import io.github.marrafon91.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
