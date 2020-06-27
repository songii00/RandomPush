package com.kakaopaycorp.api.domain.event.repository;

import org.springframework.stereotype.Repository;

import com.kakaopaycorp.api.domain.event.dto.RandomPushRequestDto;
import com.kakaopaycorp.api.domain.event.model.RandomPush;

@Repository
public class RandomPushRepository {

	public Integer save(RandomPush randomPush) {
		return null;
	}

	public RandomPush findBy(RandomPushRequestDto.Search search) {
		return null;
	}

	/**
	 * 받기 완료 금액 증가
	 *
	 * @param publishedPrice
	 */
	public void increasePublishedPrice(Integer publishedPrice) {
	}
}
