package com.kakaopaycorp.api.room.service;

import org.springframework.stereotype.Service;

import com.kakaopaycorp.api.room.repository.RoomRepository;

@Service
public class RoomService {

	private final RoomRepository roomRepository;

	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}


}
