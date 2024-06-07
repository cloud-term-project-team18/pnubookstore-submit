package org.example.pnubookstore.willdelete;

import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="auction_picture_tb")
public class AuctionPicture {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 2049)
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action= OnDeleteAction.CASCADE) // 이미지는 디비에서 삭제되는 것 뿐 아닌 S3서비스에서도 삭제되어야 함
	@JoinColumn(name="auction_id", referencedColumnName = "id")
	private Auction auction;

	@Builder
	public AuctionPicture(Long id, String url, Auction auction) {
		this.id = id;
		this.url = url;
		this.auction = auction;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AuctionPicture that = (AuctionPicture)o;
		return Objects.equals(getId(), that.getId()) && Objects.equals(getUrl(), that.getUrl());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUrl());
	}
}

