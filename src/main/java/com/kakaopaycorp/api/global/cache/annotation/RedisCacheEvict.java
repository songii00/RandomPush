package com.kakaopaycorp.api.global.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 레디스 캐시 삭제
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheEvict {
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
}
