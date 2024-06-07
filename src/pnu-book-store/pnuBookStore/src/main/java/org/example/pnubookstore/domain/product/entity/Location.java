package org.example.pnubookstore.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.pnubookstore.domain.base.AuditingEntity;
import org.example.pnubookstore.domain.product.dto.CreateProductDto;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loaction_tb")
public class Location extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "location")
    private Product product;

    @Column(nullable = false)
    private String buildingName;
    @Column(nullable = false)
    private String lockerNumber;
    @Column(nullable = false)
    private String password;

    @Builder
    public Location(Long id, Product product, String buildingName, String lockerNumber, String password) {
        this.id = id;
        this.product = product;
        this.buildingName = buildingName;
        this.lockerNumber = lockerNumber;
        this.password = password;
    }

    public void updateLocation(CreateProductDto updateProductDto){
        this.buildingName = updateProductDto.getBuildingName();
        this.lockerNumber = updateProductDto.getLockerNumber();
        this.password = updateProductDto.getPassword();
    }
}
