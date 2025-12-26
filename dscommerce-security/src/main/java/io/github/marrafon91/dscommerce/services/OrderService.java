package io.github.marrafon91.dscommerce.services;

import io.github.marrafon91.dscommerce.dto.OrderDTO;
import io.github.marrafon91.dscommerce.entities.Order;
import io.github.marrafon91.dscommerce.repositories.OrderRepository;
import io.github.marrafon91.dscommerce.services.excptions.ResourceNotFoundExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundExecption("Pedido não encontrado!!!"));
        return new OrderDTO(order);
    }
}
