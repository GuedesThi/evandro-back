package br.com.decasa.api.controllers;

import br.com.decasa.api.entities.ProductEntity;
import br.com.decasa.api.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProductEntity requestProductData(@PathVariable String id) {
        UUID idConverted = UUID.fromString(id);
        return service.productData(idConverted);
    }

}
