package com.kakaopaycorp.api.domain.event.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import com.kakaopaycorp.api.domain.event.dto.RandomPushRequestDto;


/**
 * 뿌리기
 */
@AllArgsConstructor
@Getter
@Builder
public class RandomPush {

	private Integer randomPushNo; // pk
	/**
	 * 뿌릴 금액
	 */
	private Integer pushPrice;
	/**
	 * 대화방 아이디
	 */
	private String roomId;
	/**
	 * 토큰값
	 */
	private String token;
	/**
	 * 등록 일시
	 */
	private LocalDateTime registDateTime;
	/**
	 * 등록 사용자 아이디
	 */
	private String registUserId;
	/**
	 * 받기 완료 금액
	 */
	private Integer publishedPrice;

	private List<RandomPushDetail> details;


	public RandomPush(RandomPushRequestDto request) {
		this.pushPrice = request.getTotalPushPrice();
		this.roomId = request.getRoomId();
		this.token = request.getToken();
		this.registDateTime = LocalDateTime.now();
		this.registUserId = request.getUserId();
	}
}
