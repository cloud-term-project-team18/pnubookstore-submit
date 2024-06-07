package org.example.pnubookstore.domain.product.entity;

import java.util.Objects;

import org.example.pnubookstore.domain.base.AuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 의논 사항: 사진은 auditing이 필요한가? + softDelete도 필요한건가?
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="product_picture_tb")
public class ProductPicture extends AuditingEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// http url길이 제한이 2083자이기 때문
	@Column(length = 2096, nullable = false)
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
	private Product product;

	@Builder
	public ProductPicture(String url, Product product) {
		this.url = url;
		this.product = product;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ProductPicture that = (ProductPicture)o;
		return Objects.equals(getUrl(), that.getUrl());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getUrl());
	}
}
