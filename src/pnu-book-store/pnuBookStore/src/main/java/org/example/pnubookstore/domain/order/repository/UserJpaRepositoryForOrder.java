package org.example.pnubookstore.domain.order.repository;

import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepositoryForOrder extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
}
