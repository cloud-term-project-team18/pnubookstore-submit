package org.example.pnubookstore;

import java.time.LocalDateTime;
import java.util.List;

import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.domain.order.dto.CreateOrderDto;
import org.example.pnubookstore.domain.order.service.OrderService;
import org.example.pnubookstore.domain.product.ProductExceptionStatus;
import org.example.pnubookstore.domain.product.dto.CreateProductDto;
import org.example.pnubookstore.domain.product.dto.FindProductDto;
import org.example.pnubookstore.domain.product.dto.FindProductsDto;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.entity.constant.UseStatus;
import org.example.pnubookstore.domain.product.repository.ProductJpaRepository;
import org.example.pnubookstore.domain.product.repository.SubjectCustomRepositoryImpl;
import org.example.pnubookstore.domain.product.repository.SubjectJpaRepository;
import org.example.pnubookstore.domain.product.service.ProductService;
import org.example.pnubookstore.willdelete.Auction;
import org.example.pnubookstore.domain.user.entity.User;
import org.example.pnubookstore.domain.base.constant.Role;
import org.example.pnubookstore.willdelete.repository.AuctionJpaRepository;
import org.example.pnubookstore.domain.user.repository.UserJpaRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Profile({"dev"})
@RequiredArgsConstructor
@Component
public class TestCase implements ApplicationRunner {
	private final AuctionJpaRepository auctionJpaRepository;
	private final UserJpaRepository userJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final ProductService productService;
	private final SubjectJpaRepository subjectJpaRepository;
	private final ProductJpaRepository productJpaRepository;
	private final OrderService orderService;
	private final SubjectCustomRepositoryImpl subjectCustomRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user = User.builder()
			.email("rjsdnxogh12@pusan.ac.kr")
			.password(passwordEncoder.encode("qwer1234"))
			.nickname("taeho")
			.role(Role.ROLE_USER)
			.build();
		userJpaRepository.save(user);
//		User user1 = User.builder()
//				.email("rjsdnxogh13@pusan.ac.kr")
//				.password(passwordEncoder.encode("qwer1234"))
//				.nickname("taeho1")
//				.role(Role.ROLE_USER)
//				.build();
//		userJpaRepository.save(user1);
//
//		List<Auction> auctions = List.of(
//			Auction.builder().user(user).title("제목1").description("설명1").build(),
//			Auction.builder().user(user).title("제목2").description("설명2").build(),
//			Auction.builder().user(user).title("제목3").description("설명3").build(),
//			Auction.builder().user(user).title("제목4").description("설명4").build(),
//			Auction.builder().user(user).title("제목5").description("설명5").build(),
//			Auction.builder().user(user).title("제목6").description("설명6").build(),
//			Auction.builder().user(user).title("제목7").description("설명7").build(),
//			Auction.builder().user(user).title("제목8").description("설명8").build(),
//			Auction.builder().user(user).title("제목9").description("설명9").build(),
//			Auction.builder().user(user).title("제목10").description("설명10").build(),
//			Auction.builder().user(user).title("제목11").description("설명11").build(),
//			Auction.builder().user(user).title("제목12").description("설명12").build(),
//			Auction.builder().user(user).title("제목13").description("설명13").build(),
//			Auction.builder().user(user).title("제목14").description("설명14").build(),
//			Auction.builder().user(user).title("제목15").description("설명15").build(),
//			Auction.builder().user(user).title("제목16").description("설명16").build(),
//			Auction.builder().user(user).title("제목17").description("설명17").build(),
//			Auction.builder().user(user).title("제목18").description("설명18").build(),
//			Auction.builder().user(user).title("제목19").description("설명19").build(),
//			Auction.builder().user(user).title("제목20").description("설명20").build()
//		);
//		auctionJpaRepository.saveAll(auctions);
//
//		subjectJpaRepository.save(Subject.builder()
//						.subjectName("c++")
//						.college("something")
//						.professor("park")
//						.department("computer")
//						.build());
//		subjectJpaRepository.save(Subject.builder()
//				.subjectName("c++")
//				.college("something")
//				.professor("park")
//				.department("computer")
//				.build());
//
//		List<Subject> subjects = subjectCustomRepository.findSubjects("something", null, null, null);
//		for(Subject s : subjects){
//			System.out.println(s.getCollege());
//		}
//
//		CreateProductDto createProductDto = CreateProductDto.builder()
//				.sellerEmail("rjsdnxogh12@pusan.ac.kr")
//				.productName("book1")
//				.price(10000)
//				.description("something")
//				.college("something")
//				.author("kim")
//				.underline(UseStatus.NO)
//				.note(UseStatus.NO)
//				.naming(true)
//				.discolor(true)
//				.damage(true)
//				.subjectName("c++")
//				.professor("park")
//				.department("computer")
//				.password("1")
//				.lockerNumber("1")
//				.buildingName("제6공학관")
//				.build();
//
//		productService.createProduct(createProductDto, user1);
////		Product product = productJpaRepository.findByIdFetchJoin(1L)
////						.orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));
////		productService.deleteProduct(product.getId());
//
//		CreateOrderDto createOrderDto = new CreateOrderDto(1L, "taeho1", 10);
//		orderService.createOrder(createOrderDto, user);



	}
}
