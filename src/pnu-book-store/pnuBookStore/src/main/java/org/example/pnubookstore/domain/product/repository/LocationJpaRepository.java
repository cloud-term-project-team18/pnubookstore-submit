package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationJpaRepository extends JpaRepository<Location, Long> {

}
