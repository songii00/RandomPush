package com.kakaopaycorp.api.domain.room.service;

import org.springframework.stereotype.Service;

import com.kakaopaycorp.api.domain.room.repository.RoomRepository;

@Service
public class RoomService {

	private final RoomRepository roomRepository;

	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}


}
