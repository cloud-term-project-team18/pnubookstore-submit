package org.example.pnubookstore.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.security.CustomUserDetails;
import org.example.pnubookstore.domain.order.dto.CreateOrderDto;
import org.example.pnubookstore.domain.order.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    public String createOrder(CreateOrderDto createOrderDto,
                              @AuthenticationPrincipal CustomUserDetails userDetails){
        orderService.createOrder(createOrderDto, userDetails.getUser());
        return "redirect:/";
    }
}
