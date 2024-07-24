package br.com.decasa.api.services;

import br.com.decasa.api.entities.ProductEntity;
import br.com.decasa.api.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductEntity productData(UUID productId) {
        Optional<ProductEntity> product = repository.findByIdProduct(productId);
        return product.get();
    }

}