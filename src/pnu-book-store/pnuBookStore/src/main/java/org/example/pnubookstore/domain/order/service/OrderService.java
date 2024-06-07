package org.example.pnubookstore.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.domain.order.OrderExceptionStatus;
import org.example.pnubookstore.domain.order.dto.BuyOrderDto;
import org.example.pnubookstore.domain.order.dto.CreateOrderDto;
import org.example.pnubookstore.domain.order.dto.SaleOrderDto;
import org.example.pnubookstore.domain.order.entity.Order;
import org.example.pnubookstore.domain.order.repository.OrderJpaRepository;
import org.example.pnubookstore.domain.order.repository.ProductJpaRepositoryForOrder;
import org.example.pnubookstore.domain.order.repository.UserJpaRepositoryForOrder;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final ProductJpaRepositoryForOrder productJpaRepositoryForOrder;
    private final UserJpaRepositoryForOrder userJpaRepositoryForOrder;

    @Transactional
    public void createOrder(CreateOrderDto createOrderDto, User buyer){
        Product findedProduct = productJpaRepositoryForOrder.findById(createOrderDto.getProductId())
                        .orElseThrow(() -> new Exception404(OrderExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        findedProduct.changeSaleStatus(SaleStatus.SOLD);

        if(buyer.getNickname().equals(createOrderDto.getSellerNickname())){
            throw new Exception404(OrderExceptionStatus.SAME_SELLER_BUYER.getErrorMessage());
        }

        User seller = userJpaRepositoryForOrder.findByNickname(createOrderDto.getSellerNickname())
                        .orElseThrow(() -> new Exception404(OrderExceptionStatus.USER_NOT_FOUND.getErrorMessage()));

//        User buyer = userJpaRepositoryForOrder.findByNickname(createOrderDto.getBuyerNickname())
//                .orElseThrow(() -> new Exception404(OrderExceptionStatus.USER_NOT_FOUND.getErrorMessage()));

        orderJpaRepository.save(Order.builder()
                .product(findedProduct)
                .seller(seller)
                .buyer(buyer)
                .money(createOrderDto.getPrice())
                .build());
    }

    @Transactional
    public void deleteOrder(Long orderId){
        orderJpaRepository.deleteById(orderId);
    }

//    public Page<SaleOrderDto> findSaleOrders(String nickName, int page){
//        User seller = userJpaRepositoryForOrder.findByNickname(nickName)
//                .orElseThrow(() -> new Exception404(OrderExceptionStatus.USER_NOT_FOUND.getErrorMessage()));
//
//        Pageable pageable = PageRequest.of(page, 10);
//
//        Page<Order> findOrderList = orderJpaRepository.findOrderBySeller(seller, pageable);
//
//        List<SaleOrderDto> saleOrderDtoList = findOrderList.stream()
//                .map(order -> new SaleOrderDto(
//                        order.getProduct().getProductName(),
//                        order.getBuyer().getNickname(),
//                        order.getMoney()
//                ))
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(saleOrderDtoList, pageable, findOrderList.getTotalElements());
//    }
//
//    public Page<BuyOrderDto> findBuyOrders(String nickName, int page){
//        User buyer = userJpaRepositoryForOrder.findByNickname(nickName)
//                .orElseThrow(() -> new Exception404(OrderExceptionStatus.USER_NOT_FOUND.getErrorMessage()));
//
//        Pageable pageable = PageRequest.of(page, 10);
//
//        Page<Order> findOrderList = orderJpaRepository.findOrderByBuyer(buyer, pageable);
//
//        List<BuyOrderDto> buyOrderDtoList = findOrderList.stream()
//                .map(order -> new BuyOrderDto(
//                        order.getProduct().getProductName(),
//                        order.getSeller().getNickname(),
//                        order.getMoney()
//                ))
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(buyOrderDtoList, pageable, findOrderList.getTotalElements());
//    }

}
