package org.example.pnubookstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class CreateOrderDto {
    private Long productId;
//    private String buyerNickname;
    private String sellerNickname;
    private Integer price; // Not null
}
