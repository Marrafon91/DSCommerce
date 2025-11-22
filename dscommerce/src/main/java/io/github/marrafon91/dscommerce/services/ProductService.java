package io.github.marrafon91.dscommerce.services;

import io.github.marrafon91.dscommerce.dto.ProductDTO;
import io.github.marrafon91.dscommerce.entities.Product;
import io.github.marrafon91.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).get();
        return new ProductDTO(product);
    }
}
