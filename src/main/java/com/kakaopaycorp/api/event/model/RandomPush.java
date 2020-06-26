package com.kakaopaycorp.api.event.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.kakaopaycorp.api.authorization.token.model.Token;

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
	private String tokenId;
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

	private List<Detail> details;


	public RandomPush(Request request) {
		this.pushPrice = request.getTotalPushPrice();
		this.roomId = request.getRoomId();
		this.tokenId = request.getToken().getTokenId();
		this.registDateTime = LocalDateTime.now();
		this.registUserId = request.getUserId();
	}

	@AllArgsConstructor
	@Getter
	@Builder
	public static class Detail {
		private Integer randomPushDetailNo; // pk
		private Integer randomPushNo; // autoIncrament
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

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Search {
		private String tokenId;
		private String roomId;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Request {
		private Token token;
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


	@AllArgsConstructor
	@Getter
	@Builder
	public static class Status {

		private LocalDateTime pushTime;
		private Integer pushPrice;
		private Token token;

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


}
