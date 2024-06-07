package org.example.pnubookstore.domain.product.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.entity.constant.UseStatus;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateProductDto {

//    private String sellerEmail;

    private String productName;
    private Integer price;
    private String description;
    private String author;
//    private LocalDateTime pubDate;
    private UseStatus underline;
    private UseStatus note;
    private Boolean naming;
    private Boolean discolor;
    private Boolean damage;

    // 과목
    private String college;
    private String subjectName;
    private String professor;
    private String department;

    // 사물함 위치
    private String buildingName;
    private String lockerNumber;
    private String password;

    // 아직 이미지는 넣지 않은 상태
    // 이미지는 리스트로
    private MultipartFile productPicture;
}
