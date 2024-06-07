package org.example.pnubookstore.domain.product.dto;

import lombok.*;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.entity.constant.UseStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FindProductDto {
    private Long productId;
    private String sellerName;
    private String sellerEmail;
    private String subjectName;
    private String professor;
    private String college;
    private String department;
    private String productName;
    private Integer price;
    private String description;
    private String author;
    private LocalDateTime pubDate;
    private SaleStatus saleStatus;
    private UseStatus underline;
    private UseStatus note;
    private Boolean naming;
    private Boolean discolor;
    private Boolean damage;
    private String productPictureUrl;

    private String buildingName;
    private String lockerNumber;
    private String password;

    public static FindProductDto of(Product product, String productPictureUrl){
        return FindProductDto.builder()
                .productId(product.getId())
                .sellerName(product.getSeller().getNickname())
                .sellerEmail(product.getSeller().getEmail())
                .subjectName(product.getSubject().getSubjectName())
                .professor(product.getSubject().getProfessor())
                .college(product.getSubject().getCollege())
                .department(product.getSubject().getDepartment())
                .productName(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .author(product.getAuthor())
                .pubDate(product.getPubDate())
                .underline(product.getUnderline())
                .note(product.getNote())
                .naming(product.getNaming())
                .discolor(product.getDiscolor())
                .damage(product.getDamage())
                .productPictureUrl(productPictureUrl)
                .buildingName(product.getLocation().getBuildingName())
                .lockerNumber(product.getLocation().getLockerNumber())
                .password(product.getLocation().getPassword())
                .build();
    }
}
