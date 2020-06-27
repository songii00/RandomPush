package com.kakaopaycorp.api.domain.event.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.kakaopaycorp.api.domain.event.model.RandomPush;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RandomPushRequestDto {
	/**
	 * 토큰
	 */
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

	public RandomPush toEntity() {
		return RandomPush.builder()
						 .pushPrice(this.totalPushPrice)
						 .roomId(this.roomId)
						 .token(this.token)
						 .registDateTime(LocalDateTime.now())
						 .registUserId(this.userId)
						 .build();
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Search {
		private String token;
		private String roomId;
		private String userId;
	}


	@AllArgsConstructor
	@Getter
	@Builder
	public static class Status {

		private LocalDateTime pushTime;
		private Integer pushPrice;
		private String token;

		/**
		 * 받기완료 금액
		 */
		private Integer publishedPrice;
		private List<PublishedInfo> publishedInfos;

		/**
		 * 받기 완료된 정보
		 */
		@AllArgsConstructor
		@Getter
		public static class PublishedInfo {
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


}
