package org.example.pnubookstore.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FindUserDto {
    private String nickname;
    private String email;
}
