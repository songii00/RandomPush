package com.kakaopaycorp.api.authorization.token.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

import com.kakaopaycorp.api.authorization.token.model.Token;
import com.kakaopaycorp.api.authorization.token.service.TokenPublishService;
import com.kakaopaycorp.api.event.model.RandomPush;

@Component
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {
	private static final String TOKEN_ATTRIBUTE_NAME = "token";
	private final TokenPublishService tokenPublishService;

	public TokenInterceptor(TokenPublishService tokenPublishService) {
		this.tokenPublishService = tokenPublishService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		String userId = request.getParameter(RandomPush.Request.getNameFromUserId());
		String roomId = request.getParameter(RandomPush.Request.getNameFromRoomId());

		// 토큰 발급
		Token token = tokenPublishService.publishToken(roomId, userId);
		request.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
		log.info("token create : {}", token);
		return true;
	}
}
