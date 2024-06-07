package org.example.pnubookstore.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
@Getter
public class FindProductsDto {
    private Long productId;
    private String productName;
    private String seller;
    private Integer price;
}
