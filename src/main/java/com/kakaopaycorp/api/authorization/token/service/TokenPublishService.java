package com.kakaopaycorp.api.authorization.token.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.kakaopaycorp.api.authorization.token.model.Token;
import com.kakaopaycorp.api.authorization.token.model.TokenKeygen;
import com.kakaopaycorp.api.authorization.token.repository.TokenRepository;

@Service
public class TokenPublishService {

	private final TokenRepository tokenRepository;

	public TokenPublishService(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}

	/**
	 * 토큰 발급
	 *
	 * @return
	 */
	public Token publishToken(String roomId, String userId) {
		Token token = publish(roomId, userId);
		save(token);
		return token;
	}

	public Token publish(String roomId, String userId) {
		String tokenId = TokenKeygen.publishToken();
		return Token.builder()
					.roomId(roomId)
					.userId(userId)
					.registDateTime(LocalDateTime.now())
					.tokenId(tokenId)
					.build();
	}

	public void save(Token token) {
		token.crypt();
		tokenRepository.save(token);
	}
}
