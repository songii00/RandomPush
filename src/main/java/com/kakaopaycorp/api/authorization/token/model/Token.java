package com.kakaopaycorp.api.authorization.token.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Token {

	private String tokenId;
	private String userId;
	private String roomId;
	private LocalDateTime registDateTime;

	public boolean isExpired() {
		return this.registDateTime.plusMinutes(10).isBefore(LocalDateTime.now());
	}

	public void crypt() {
		this.tokenId = TokenKeygen.getHashKeyBy(this.getTokenId());
	}

	@Getter
	@AllArgsConstructor

	public static class Response {

		public static final Response FAIL = new Response("", "");
		/**
		 * 토큰값
		 */
		private String accessToken;
		/**
		 * 만료시간
		 */
		private String expireIn;
	}

	public static class Request {


	}
}
