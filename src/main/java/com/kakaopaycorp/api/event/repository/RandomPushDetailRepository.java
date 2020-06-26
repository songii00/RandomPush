package com.kakaopaycorp.api.event.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kakaopaycorp.api.event.model.RandomPush;

@Repository
public class RandomPushDetailRepository {

	public Integer save(RandomPush.Detail detail) {
		return null;
	}

	public Integer save(List<RandomPush.Detail> randomPushDetails) {
		return null;
	}

	public List<RandomPush.Detail> findByRandomPushNo(Integer randomPushNo) {
		return null;
	}

	public List<RandomPush.Detail> findBy(String tokenId, String roomId) {
		return null;
	}
}
