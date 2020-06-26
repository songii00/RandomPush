package com.kakaopaycorp.api.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AuthorizaionConfig implements WebMvcConfigurer {

	@Autowired
	@Qualifier(value = "tokenInterceptor")
	private HandlerInterceptor tokenInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor)
				.addPathPatterns("/event/**")
				.excludePathPatterns("/user");
	}
}
