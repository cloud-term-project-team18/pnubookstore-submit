package org.example.pnubookstorebatch.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 생성시간
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(updatable = false)
	protected LocalDateTime createdAt;

	// 수정시간
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@LastModifiedDate
	protected LocalDateTime lastModifiedAt;

	@ColumnDefault(value = "false")
	Boolean isDeleted = false;

	public void delete() {
		isDeleted = true;
	}

	public void restore() {
		isDeleted = false;
	}
}
