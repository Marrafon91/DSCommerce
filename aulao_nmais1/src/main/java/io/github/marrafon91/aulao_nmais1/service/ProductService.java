package io.github.marrafon91.aulao_nmais1.service;

import io.github.marrafon91.aulao_nmais1.dto.ProductDTO;
import io.github.marrafon91.aulao_nmais1.entities.Product;
import io.github.marrafon91.aulao_nmais1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> find(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(ProductDTO::new);
    }
}
