package com.kakaopaycorp.api.domain.event.model;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RandomPushDetail {

	private Integer randomPushDetailNo;
	private Integer randomPushNo;
	/**
	 * 분배 금액
	 */
	private Integer publishedPrice;
	/**
	 * 사용 여부
	 */
	private boolean useYn;
	private LocalDateTime registDateTime;
	private String registUserId;
	/**
	 * 발급 사용자 아이디
	 */
	private String publishUserId;

	public void setRandomPushNo(Integer randomPushNo) {
		this.randomPushNo = randomPushNo;
	}

	public void publish(String userId) {
		this.useYn = true;
		this.publishUserId = userId;
	}
}
