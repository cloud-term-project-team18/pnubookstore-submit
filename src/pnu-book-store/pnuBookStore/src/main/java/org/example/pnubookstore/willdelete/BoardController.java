package org.example.pnubookstore.willdelete;

import org.example.pnubookstore.core.security.CustomUserDetails;
import org.example.pnubookstore.willdelete.service.AuctionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

	private final AuctionService auctionService;

	@GetMapping("/auction")
	public String getAuctionPage(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
		Model model
	) {
		model.addAttribute("boardList", auctionService.getAuctionBoard(pageable));
		return "board/auction-board";
	}

	@GetMapping("/auction/{auctionId}")
	public String getAuctionDetail(
		@PathVariable Long auctionId,
		Model model
	) {
		model.addAttribute("auction", auctionService.getAuctionDetail(auctionId));
		return "board/auction-detail";
	}

	@GetMapping("/free")
	public String getFreePage() {
		return "board/free-board";
	}
}
