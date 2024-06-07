package org.example.pnubookstore.domain.product;

import org.example.pnubookstore.core.exception.BaseExceptionStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductExceptionStatus implements BaseExceptionStatus {
	// 도메인에 맞는 예외를 채워주세요.
	USER_NOT_FOUND(404, "회원 정보를 찾을 수 없습니다."),
	SUBJECT_NOT_FOUND(404, "과목 정보를 찾을 수 없습니다."),
	PRODUCT_NOT_FOUND(404, "물품 정보를 찾을 수 없습니다."),
	PRODUCT_PICTURES_NOT_FOUND(404, "물품 사진 정보를 찾을 수 없습니다."),
	ORDER_NOT_FOUND(404, "주문 정보를 찾을 수 없습니다."),
	;
	private final int errorCode;
	private final String errorMessage;
}
