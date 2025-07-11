package com.example.rentmanager.service;

import com.example.rentmanager.entity.Room;
import com.example.rentmanager.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class RoomService {

	private final RoomRepository roomRepository;

	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
}
