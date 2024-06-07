package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductPictureJpaRepository extends JpaRepository<ProductPicture, Long> {
    Optional<List<ProductPicture>> findAllByProduct(Product product);
    Optional<ProductPicture> findByProduct(Product product);

    void deleteAllByProduct(Product product);
}
