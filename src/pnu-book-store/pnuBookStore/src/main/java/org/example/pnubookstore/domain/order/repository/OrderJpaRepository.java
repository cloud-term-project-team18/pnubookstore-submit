package org.example.pnubookstore.domain.order.repository;

import org.example.pnubookstore.domain.order.entity.Order;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

//    List<Order> findOrderBySeller(User seller);
    Page<Order> findOrderByBuyer(User buyer, Pageable pageable);
    Optional<Order> findOrderByBuyerAndProduct(User buyer, Product product);
}
