package org.example.pnubookstore.willdelete.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.example.pnubookstore.willdelete.Auction;

import lombok.Builder;

public record AuctionDetailDto(
	List<String> images,
	String writer,
	String title,
	String description,
	String createAt,
	String modifiedAt
) {

	@Builder
	public AuctionDetailDto {
	}

	public static AuctionDetailDto of(Auction auction) {
		return AuctionDetailDto.builder()
			.writer(auction.getUser().getEmail())
			.title(auction.getTitle())
			.description(auction.getDescription())
			.createAt(auction.getCreatedAt()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
			.modifiedAt(auction.getLastModifiedAt()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
			.build();
	}
}
