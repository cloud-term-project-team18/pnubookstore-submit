package org.example.pnubookstore.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class BuyProductDto {
    private Long productId;
    private String productName;
    private Integer price;
    private String seller;
}
