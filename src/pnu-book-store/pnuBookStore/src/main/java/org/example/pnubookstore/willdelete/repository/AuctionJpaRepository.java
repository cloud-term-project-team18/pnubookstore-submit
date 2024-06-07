package org.example.pnubookstore.willdelete.repository;

import java.util.Optional;

import org.example.pnubookstore.willdelete.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionJpaRepository extends JpaRepository<Auction, Long> {
	@Query(value = "select a from Auction a "
	, countQuery = "select count(a.id) from Auction a")
	Page<Auction> findAllPaginated(Pageable pageable);

	@Query(value = "select a from Auction a "
		+ "join fetch a.user u "
		+ "where a.id = :auctionId")
	Optional<Auction> findByIdFetchJoin(@Param("auctionId") Long id);
}
