package org.example.pnubookstore.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;

@Getter
@AllArgsConstructor
@Builder
public class SaleProductDto {
    private Long productId;
    private String productName;
    private Integer price;
    private SaleStatus saleStatus;
}
