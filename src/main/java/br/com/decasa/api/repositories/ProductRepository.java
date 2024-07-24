package br.com.decasa.api.repositories;

import br.com.decasa.api.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByIdProduct(UUID idProduct);
}