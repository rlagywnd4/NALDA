package com.sparta.nalda.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST_ERROR(400, "Bad Request Exception"),
    NOT_FOUND_ERROR(404, "Not Found Exception"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error Exception"),

    /**
     * ******************************* 400 Error Code ***************************************
     */

    EMAIL_ALREADY_EXISTS(400, "이미 사용중인 이메일입니다."),
    INVALID_PASSWORD(400, "비밀번호가 일치하지 않습니다."),
    WITHDRAWN_USER(400, "사용할 수 없는 이메일입니다."),
    INVALID_EMPTY(400, "값이 비었습니다."),
    TIME_OVER(400, "주문 가능한 시간이 아닙니다."),
    MIN_ORDER_PRICE_NOT_OVER(400, "최소 주문 금액을 채워주세요."),

    /**
     * ******************************* 404 Error Code ***************************************
     */

    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    STORE_NOT_FOUND(404, "가게를 찾을 수 없습니다."),
    MENU_NOT_FOUND(404, "매뉴를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(404, "주문을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(404, "리뷰를 찾을 수 없습니다."),



    ;



    private final int status;
    private final String message;

}
