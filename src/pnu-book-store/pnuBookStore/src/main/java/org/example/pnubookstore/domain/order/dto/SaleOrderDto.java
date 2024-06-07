package org.example.pnubookstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaleOrderDto {
    private String productName;
    private String buyer;
    private int price;
}
