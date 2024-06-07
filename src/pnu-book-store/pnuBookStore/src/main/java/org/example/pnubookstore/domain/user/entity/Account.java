package org.example.pnubookstore.domain.user.entity;

import java.util.Objects;

import org.example.pnubookstore.domain.base.AuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="account_tb")
public class Account extends AuditingEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
	private User user;

	@Column(nullable = false, length = 20)
	private String accountNum;

	@Builder
	public Account(Long id, User user, String accountNum) {
		this.id = id;
		this.user = user;
		this.accountNum = accountNum;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Account account = (Account)o;
		return Objects.equals(getId(), account.getId()) && Objects.equals(getAccountNum(),
			account.getAccountNum());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getAccountNum());
	}
}