package com.example.rentmanager.controller;

import com.example.rentmanager.dto.CaculateElectricReq;
import com.example.rentmanager.dto.CreateElectricRecord;
import com.example.rentmanager.service.ElectricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/electric")
@RequiredArgsConstructor
public class ElectricController {

	private final ElectricService electricService;

	@PostMapping("/save")
	public ResponseEntity<Object> saveWashUsed(@RequestBody List<CreateElectricRecord> reqList) {
		try {
			electricService.saveElectric(reqList);
			return ResponseEntity.ok().body("Thanh cong");
		} catch (Exception e) {
			return ResponseEntity.ok().body("That bai: " + e.getMessage());
		}
	}

	@GetMapping("/list")
	public ResponseEntity<Object> getElectric() {
		try {
			return ResponseEntity.ok()
					.body(electricService.findAll());
		} catch (Exception e) {
			return ResponseEntity.ok().body("That bai: " + e.getMessage());
		}
	}

	@PostMapping("/calculate")
	public ResponseEntity<Object> calculateElectric(@RequestBody CaculateElectricReq req) {
		try {

			return ResponseEntity.ok()
					.body(electricService.calculateElectric(req));
		} catch (Exception e) {
			return ResponseEntity.ok().body("That bai: " + e.getMessage());
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<Object> calculateElectric() {
		try {
			return ResponseEntity.ok()
					.body(new HashMap<>().put("fileId",electricService.uploadToDrive()));
		} catch (Exception e) {
			return ResponseEntity.ok().body("That bai: " + e.getMessage());
		}
	}
}
