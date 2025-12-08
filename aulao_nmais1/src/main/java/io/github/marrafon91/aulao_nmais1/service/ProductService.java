package io.github.marrafon91.aulao_nmais1.service;

import io.github.marrafon91.aulao_nmais1.dto.PageDTO;
import io.github.marrafon91.aulao_nmais1.dto.ProductDTO;
import io.github.marrafon91.aulao_nmais1.entities.Product;
import io.github.marrafon91.aulao_nmais1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public PageDTO<ProductDTO> find(PageRequest pageRequest) {

        Page<Product> page = repository.findAll(pageRequest);

        repository.findProductsCategories(page.getContent());
        List<ProductDTO> dtoList = page.getContent().stream().map(ProductDTO::new).toList();

        return new PageDTO<>(
                dtoList,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
