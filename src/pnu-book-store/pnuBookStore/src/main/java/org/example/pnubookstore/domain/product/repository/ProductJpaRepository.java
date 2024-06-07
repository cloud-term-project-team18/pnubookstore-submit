package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p "
        + "join fetch p.subject "
        + "where p.id = :productId")
    Optional<Product> findByIdFetchJoin(@Param("productId") Long productId);

    List<Product> findAll();

    Page<Product> findBySubjectIn(Pageable pageable, List<Subject> subjects);

    Page<Product> findBySeller(Pageable pageable, User seller);
}
