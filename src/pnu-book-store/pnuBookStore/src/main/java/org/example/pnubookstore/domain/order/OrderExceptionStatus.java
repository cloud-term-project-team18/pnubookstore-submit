package org.example.pnubookstore.domain.order;

import org.example.pnubookstore.core.exception.BaseExceptionStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderExceptionStatus implements BaseExceptionStatus {
	// 도메인에 맞는 예외를 채워주세요.
	MONEY_MUST_POSITIVE(400, "금액은 무조건 양수값이어야 합니다."),
	SAME_SELLER_BUYER(400, "판매자와 구매자가 같은 사람입니다."),
	USER_NOT_FOUND(404, "회원 정보를 찾을 수 없습니다."),
	PRODUCT_NOT_FOUND(404, "물품 정보를 찾을 수 없습니다."),
	;
	private final int errorCode;
	private final String errorMessage;
}
