package com.kakaopaycorp.api.domain.event.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kakaopaycorp.api.domain.event.model.RandomPushDetail;

@Repository
public class RandomPushDetailRepository {

	public Integer save(RandomPushDetail detail) {
		return null;
	}

	public Integer save(List<RandomPushDetail> randomPushDetails) {
		return null;
	}

	public List<RandomPushDetail> findByRandomPushNo(Integer randomPushNo) {
		return null;
	}
}
