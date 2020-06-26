package com.kakaopaycorp.api.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RandomPushSearchDto {
	private String token;
	private String roomId;
	private String userId;
}
