package org.example.pnubookstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuyOrderDto {
    private String productName;
    private String seller;
    private int price;
}
