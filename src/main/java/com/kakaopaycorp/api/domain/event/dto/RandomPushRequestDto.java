package com.kakaopaycorp.api.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RandomPushRequestDto {
	private String token;
	/**
	 * 뿌릴 금액
	 */
	private Integer totalPushPrice;
	/**
	 * 뿌릴 인원
	 */
	private Integer userCount;

	private String roomId;
	private String userId;

	public static String getNameFromUserId() {
		return "userId";
	}

	public static String getNameFromRoomId() {
		return "roomId";
	}
}
