package com.example.rentmanager.service;

import com.example.rentmanager.dto.LaundryDto;
import com.example.rentmanager.entity.LaundryRecord;
import com.example.rentmanager.repository.LaundryRepository;
import com.example.rentmanager.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaundryService {

	private final LaundryRepository laundryRepository;
	private final RoomRepository roomRepository;
	public void saveUsed(Long roomId) {
		laundryRepository.save(LaundryRecord.builder()
				.roomId(roomId)
				.build());
	}

	public List<LaundryDto> statsByMonth(String month) {
		String parts[] =month.split("-");
		int year = Integer.parseInt(parts[0]);
		int monthNumber = Integer.parseInt(parts[1]);
		List<LaundryDto> laundryRecords = laundryRepository.findByCreatedAtYearAndCreatedAtMonth(year, monthNumber);
		for( LaundryDto laundryDto : laundryRecords) {
			laundryDto.setRoomId(roomRepository.findByRoomName(laundryDto.getRoomName()).get().getId());
			laundryDto.setDetailTime(findAllByRoomName(laundryDto.getRoomId(),monthNumber,year));
		}
		return laundryRecords;
	}

	public List<LaundryRecord> findAllByRoomName(Long roomId,int month,int year) {
		return laundryRepository.findByRoomId(roomId).stream()
				.filter(laundryRecord -> laundryRecord.getCreatedAt().getMonthValue() == month
						&& laundryRecord.getCreatedAt().getYear() == year)
				.toList();
	}
}