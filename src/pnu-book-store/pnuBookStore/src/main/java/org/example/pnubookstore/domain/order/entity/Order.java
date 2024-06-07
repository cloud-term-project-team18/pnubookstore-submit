package org.example.pnubookstore.domain.order.entity;

import java.util.Objects;

import org.example.pnubookstore.core.exception.Exception400;
import org.example.pnubookstore.domain.base.AuditingEntity;
import org.example.pnubookstore.domain.order.OrderExceptionStatus;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.user.entity.User;

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

// SoftDelete때문에 Unique설정을 따로하지 않았습니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_tb")
public class Order extends AuditingEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", nullable=false, referencedColumnName = "id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="buyer", nullable = false,  referencedColumnName= "id")
	private User buyer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="seller", nullable = false, referencedColumnName = "id")
	private User seller;
	private int money; // Not null

	@Builder
	public Order(Long id, Product product, User buyer, User seller, int money) {
		this.id = id;
		this.product = product;
		validTrade(seller, buyer);
		this.buyer = buyer;
		this.seller = seller;
		this.money = checkValidMoney(money);
	}

	private int checkValidMoney(int money) {
		if (money < 0)
			throw new Exception400(OrderExceptionStatus.MONEY_MUST_POSITIVE);
		return money;
	}

	private void validTrade(User seller, User buyer) {
		if (seller.getId().equals(buyer.getId())) {
			throw new Exception400(OrderExceptionStatus.SAME_SELLER_BUYER);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Order order = (Order)o;
		return Objects.equals(getId(), order.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
}
