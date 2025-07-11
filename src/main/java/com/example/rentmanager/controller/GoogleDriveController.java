package com.example.rentmanager.controller;

import com.example.rentmanager.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/drive")
public class GoogleDriveController {

	private final GoogleDriveService googleDriveService;

	@Autowired
	public GoogleDriveController(GoogleDriveService googleDriveService) {
		this.googleDriveService = googleDriveService;
	}

	// API upload file lên Google Drive
//	@PostMapping("/upload")
//	public ResponseEntity<?> uploadFileToDrive(@RequestParam("file") MultipartFile file) {
//		try {
//			String fileId = googleDriveService.uploadFile(file);
//			return ResponseEntity.ok("Upload thành công! Google Drive File ID: " + fileId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Upload thất bại: " + e.getMessage());
//		}
//	}
}
