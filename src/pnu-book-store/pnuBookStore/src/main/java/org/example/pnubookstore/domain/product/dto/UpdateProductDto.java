package org.example.pnubookstore.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.entity.constant.UseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateProductDto {

    private String sellerEmail;
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

    private String subjectName;
    private String professor;
    private String department;

    // 이미지 추후 추가 예정
    private List<MultipartFile> productPictureList;
}
