package com.kakaopaycorp.api.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResultDto<T> {

	private static final String SUCCESS_CODE = "0";
	private static final String FAIL_CODE = "9999";

	private String code;
	private String errorMessage;
	private T result;

	public ApiResultDto(T result) {
		this(SUCCESS_CODE, "", result);
	}

	public static ApiResultDto fail(String errorMessage) {
		return new ApiResultDto<>(FAIL_CODE, errorMessage, null);
	}

	public static ApiResultDto fail() {
		return new ApiResultDto<>(FAIL_CODE, "error", null);
	}

}
