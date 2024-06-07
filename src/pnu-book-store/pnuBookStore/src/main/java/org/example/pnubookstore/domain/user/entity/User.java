package org.example.pnubookstore.domain.user.entity;

import java.util.Objects;

import org.example.pnubookstore.domain.base.AuditingEntity;
import org.example.pnubookstore.domain.base.constant.Role;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class User extends AuditingEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Column(nullable = false, length = 50, unique = true)
	private String nickname;

	@ColumnDefault("false")
	private Boolean canSale = false;

	// 판매와, 이메일 검증은 생성자에서 제외, 특정 조건에 의해서만 값 변경을 허가하기 위함.
	@Builder
	public User(Long id, String email, String password, Role role, String nickname) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return Objects.equals(getId(), user.getId()) && Objects.equals(getEmail(),
			user.getEmail());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getEmail());
	}
}
