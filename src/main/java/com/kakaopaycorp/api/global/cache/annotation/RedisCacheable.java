package com.kakaopaycorp.api.global.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 레디스 캐시 조회 / 생성
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheable {
	/**
	 * 구분자
	 *
	 * @return
	 */
	String prefix() default "";

	/**
	 * 아이디
	 *
	 * @return
	 */
	String[] ids();

	/**
	 * 만료시간
	 *
	 * @return
	 */
	int expireTime() default 300;
}
