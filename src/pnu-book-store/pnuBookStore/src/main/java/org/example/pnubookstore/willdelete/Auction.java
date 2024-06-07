package org.example.pnubookstore.willdelete;

import java.util.Objects;

import org.example.pnubookstore.domain.base.AuditingEntity;
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


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="auction_tb")
public class Auction extends AuditingEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
	private User user;
	private String title;
	private String description;

	@Builder
	public Auction(Long id, User user, String title, String description) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Auction auction = (Auction)o;
		return Objects.equals(getId(), auction.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
}
