package com.example.rentmanager.controller;

import com.example.rentmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

	private final RoomService roomService;

	@GetMapping("list")
	public ResponseEntity<Object> getAllRooms() {

		return ResponseEntity.ok().body(roomService.getAllRooms());
	}
}
