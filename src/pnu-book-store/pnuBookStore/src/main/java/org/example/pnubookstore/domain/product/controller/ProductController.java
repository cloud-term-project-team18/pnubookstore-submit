package org.example.pnubookstore.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.security.CustomUserDetails;
import org.example.pnubookstore.domain.product.dto.CreateProductDto;
import org.example.pnubookstore.domain.product.service.ProductService;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 물품 리스트
    @GetMapping(value = "/products")
    public String products(){
        return "board/auction-board.html";
    }


    // 물품 조회 페이지
    @GetMapping(value = "/products/{productId}")
    public String productDetail(Model model, @PathVariable("productId") Long productId){
        model.addAttribute("product", productService.findProduct(productId));

        return "board/auction-detail.html";
    }

    @GetMapping(value = "/product-register")
    public String productPage(){
        // 물품 등록 페이지
        return "board/product-register.html";
    }

    @PostMapping(value = "/product-register")
    public String registerProduct(CreateProductDto createProductDto,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        productService.createProduct(createProductDto, userDetails.getUser());

        return "board/auction-board.html";
    }

    @GetMapping(value = "/myPage")
    public String myPage(Model model, @RequestParam(value="page", defaultValue="0") int page,
                         @AuthenticationPrincipal CustomUserDetails userDetails){
        model.addAttribute("user", productService.findUserInfo(userDetails.getUser()));
        model.addAttribute("buyProducts", productService.findBuyProducts(page, userDetails.getUser()));
        model.addAttribute("saleProducts", productService.findSaleProducts(page, userDetails.getUser()));
        return "myPage.html";
    }

    @GetMapping(value = "/myPage/buyProducts/{productId}")
    public String buyProductDetail(Model model, @PathVariable("productId") Long productId,
                                   @AuthenticationPrincipal CustomUserDetails userDetails){
        model.addAttribute("product", productService.findBuyProduct(productId, userDetails.getUser()));
        return "board/buy-auction-detail.html"; // 임시 파일명
    }
}
