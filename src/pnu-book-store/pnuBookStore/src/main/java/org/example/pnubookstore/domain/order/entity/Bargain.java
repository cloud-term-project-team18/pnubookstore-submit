package org.example.pnubookstore.domain.order.entity;

import java.util.Objects;

import org.example.pnubookstore.core.exception.Exception400;
import org.example.pnubookstore.domain.base.AuditingEntity;
import org.example.pnubookstore.domain.order.OrderExceptionStatus;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.user.entity.User;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="bargain_tb")
public class Bargain extends AuditingEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="seller", nullable=false, referencedColumnName = "id")
	private User seller;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="buyer", nullable = false, referencedColumnName = "id")
	private User buyer;
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	private int bargainPrice;

	// 승인은 기본적으로 false, 이후 비지니스 로직에서 변경할 수 있도록 한다.
	@ColumnDefault("false")
	private Boolean isApproved = false;

	public Bargain(Long id, User seller, User buyer, Product product, int bargainPrice) {
		this.id = id;
		validTrade(seller, buyer);
		this.seller = seller;
		this.buyer = buyer;
		this.product = product;
		this.bargainPrice = checkValidMoney(bargainPrice);
	}

	private void validTrade(User seller, User buyer) {
		if (seller.getId().equals(buyer.getId())) {
			throw new Exception400(OrderExceptionStatus.SAME_SELLER_BUYER);
		}
	}

	private int checkValidMoney(int money) {
		if (money < 0)
			throw new Exception400(OrderExceptionStatus.MONEY_MUST_POSITIVE);
		return money;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Bargain bargain = (Bargain)o;
		return Objects.equals(getId(), bargain.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
}
