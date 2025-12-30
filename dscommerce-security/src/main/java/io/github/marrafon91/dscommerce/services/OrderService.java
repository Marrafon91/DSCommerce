package io.github.marrafon91.dscommerce.services;

import io.github.marrafon91.dscommerce.dto.OrderDTO;
import io.github.marrafon91.dscommerce.dto.OrderItemDTO;
import io.github.marrafon91.dscommerce.entities.Order;
import io.github.marrafon91.dscommerce.entities.OrderItem;
import io.github.marrafon91.dscommerce.entities.Product;
import io.github.marrafon91.dscommerce.entities.User;
import io.github.marrafon91.dscommerce.entities.enums.OrderStatus;
import io.github.marrafon91.dscommerce.repositories.OrderItemRepository;
import io.github.marrafon91.dscommerce.repositories.OrderRepository;
import io.github.marrafon91.dscommerce.repositories.ProductRepository;
import io.github.marrafon91.dscommerce.services.excptions.ResourceNotFoundExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundExecption("Pedido não encontrado!!!"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }
        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
