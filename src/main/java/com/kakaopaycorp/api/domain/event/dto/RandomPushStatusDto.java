package com.kakaopaycorp.api.domain.event.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RandomPushStatusDto {

	private LocalDateTime pushTime;
	private Integer pushPrice;
	private String token;

	/**
	 * 받기완료 금액
	 */
	private Integer publishedPrice;
	private List<publishedInfo> publishedInfos;

	/**
	 * 받기 완료된 정보
	 */
	@AllArgsConstructor
	@Getter
	public static class publishedInfo {
		/**
		 * 받은 금액
		 */
		private Integer publishedPrice;
		/**
		 * 받은 사용자 아이디
		 */
		private String userId;
	}

}
