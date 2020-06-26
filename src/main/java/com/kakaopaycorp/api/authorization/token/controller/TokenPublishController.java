package com.kakaopaycorp.api.authorization.token.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopaycorp.api.authorization.token.model.Token;

@RestController
public class TokenPublishController {

	/**
	 * 토큰 발급
	 *
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/token/publish")
	public Token.Response publishToken(@RequestBody Token.Request request) {
		return Token.Response.FAIL;
	}
}
