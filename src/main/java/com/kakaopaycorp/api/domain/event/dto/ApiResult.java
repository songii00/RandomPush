package com.kakaopaycorp.api.domain.event.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResult<T> {

	private static final String SUCCESS_CODE = "0";
	private static final String FAIL_CODE = "9999";

	private String code;
	private String errorMessage;
	private T result;

	public ApiResult(T result) {
		this(SUCCESS_CODE, "", result);
	}

	public static ApiResult fail(String errorMessage) {
		return new ApiResult<>(FAIL_CODE, errorMessage, null);
	}

	public static ApiResult fail() {
		return new ApiResult<>(FAIL_CODE, "error", null);
	}

}
