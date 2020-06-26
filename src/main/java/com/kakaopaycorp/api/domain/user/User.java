package com.kakaopaycorp.api.domain.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
	private String userId;
	private String userName;
	private LocalDateTime registDateTime;
	private String roomId;
}
