package io.github.marrafon91.aulao_nmais1.controller;

import io.github.marrafon91.aulao_nmais1.dto.PageDTO;
import io.github.marrafon91.aulao_nmais1.dto.ProductDTO;
import io.github.marrafon91.aulao_nmais1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<PageDTO<ProductDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "05") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page,size);
        PageDTO<ProductDTO> dto = service.find(pageRequest);

        return ResponseEntity.ok(dto);
    }
}
