package com.example.rentmanager.controller;

import com.example.rentmanager.service.LaundryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/laundry")
@RequiredArgsConstructor
public class LaundryController {

	private final LaundryService laundryService;

	@GetMapping("/save")
	public ResponseEntity<Object> saveWashUsed(@RequestParam Long roomId) {
		laundryService.saveUsed(roomId);
		return ResponseEntity.ok().body("Thanh cong");
	}

	@GetMapping("/stats")
	public ResponseEntity<Object> statsByMonth(@RequestParam String month) {

		return ResponseEntity.ok().body(laundryService.statsByMonth(month));
	}


}
