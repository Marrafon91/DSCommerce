package io.github.marrafon91.dscommerce.controllers;

import io.github.marrafon91.dscommerce.dto.OrderDTO;
import io.github.marrafon91.dscommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> findById(@PathVariable("id") Long id) {
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

}
