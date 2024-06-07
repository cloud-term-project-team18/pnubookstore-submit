package org.example.pnubookstore.willdelete.service;

import java.util.List;

import org.example.pnubookstore.willdelete.dto.AuctionBoardDto;
import org.example.pnubookstore.willdelete.dto.AuctionDetailDto;
import org.example.pnubookstore.willdelete.repository.AuctionJpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
	private final AuctionJpaRepository auctionJpaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<AuctionBoardDto> getAuctionBoard(Pageable pageable) {
		return auctionJpaRepository.findAllPaginated(pageable)
			.map(auction -> new AuctionBoardDto(auction.getId(), auction.getTitle()))
			.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public AuctionDetailDto getAuctionDetail(Long auctionId) {
		return AuctionDetailDto.of(auctionJpaRepository.findByIdFetchJoin(auctionId)
			.orElseThrow( () -> new RuntimeException("에러")));
	}
}
