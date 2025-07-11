package com.example.rentmanager.dto;

import com.example.rentmanager.entity.LaundryRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class LaundryDto {
	private Long roomId;
	private String roomName;
	private Long count;
	private List<LaundryRecord> detailTime;

	public LaundryDto(String roomName, Long count) {
		this.roomId = null;
		this.roomName = roomName;
		this.count = count;
		this.detailTime = null;
	}
}
