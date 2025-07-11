package com.example.rentmanager.service;

import com.example.rentmanager.dto.*;
import com.example.rentmanager.entity.ElectricityRecord;
import com.example.rentmanager.repository.ElectricRepository;
import com.example.rentmanager.repository.RoomRepository;
import com.example.rentmanager.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectricService {

	private final ElectricRepository electricRepository;
	private final LaundryService laundryService;
	private final RoomRepository roomRepository;
	private final GoogleDriveService googleDriveService;

	public void saveElectric(List<CreateElectricRecord> reqList) {
//		int month = LocalDate.now().getMonthValue();
		for (CreateElectricRecord req : reqList) {
			ElectricityRecord e = electricRepository.findByRoomId(req.getRoomId());
			int oldElectric = e != null ? e.getEndElectric() : 0;
			e.setStartElectric(oldElectric);
			e.setEndElectric(req.getElectric());
			electricRepository.save(e);
		}
	}

	public List<ElectricityRecord> getElectricByRoomIdAndMonth(Long roomId, int month, int year) {
		return electricRepository.findByRoomIdAndMonthAndYear(roomId, month, year);
	}

	public List<ElectricityRecord> findAll() {
		return electricRepository.findAll();
	}

	public CaculateElectricShareInfo calculateElectric(CaculateElectricReq req) {
		double pricePerUnit = req.getTotalMoney() / req.getTotalElectric();
		List<CaculateElectricResp> resp = new ArrayList<>();
		List<ElectricityRecord> electricityRecords = electricRepository.findAll();
		List<LaundryDto> laundryByRoom = laundryService.statsByMonth(req.getMonth());
		double roomElectric = 0;
		double shareMoney = 0;
		for (ElectricityRecord ce : electricityRecords) {
			roomElectric += ce.getEndElectric() - ce.getStartElectric();
		}
		double shareElectric = req.getTotalElectric() - roomElectric;
		shareMoney = (shareElectric * pricePerUnit) / 4;

		for (ElectricityRecord el : electricityRecords) {
			LaundryDto laundryDto = laundryByRoom.stream()
					.filter(m -> m.getRoomId().equals(el.getRoomId()))
					.findFirst()
					.orElse(null);
			double electricityUsedInLaundry = laundryDto != null ? laundryDto.getCount() * 0.7 : 0;
			double totalElectricityUsed = el.getEndElectric() - el.getStartElectric() + electricityUsedInLaundry;
			double totalMoney = totalElectricityUsed * pricePerUnit + shareMoney;
			resp.add(CaculateElectricResp.builder()
					.elctricityUsedInLaundry(electricityUsedInLaundry)
					.totalElectricUsed(totalElectricityUsed)
					.roomId(el.getRoomId())
					.roomName(roomRepository.findById(el.getRoomId()).orElse(null).getRoomName())
					.totalMoney(totalMoney)
					.build());
		}

		return CaculateElectricShareInfo.builder()
				.pricePerUnit(pricePerUnit)
				.shareElectric(shareElectric)
				.shareMoney(shareMoney)
				.electricDetails(resp)
				.build();
	}

	public String uploadToDrive() {

		String fileId = null;
		try {
			Resource resource = new ClassPathResource("templates/template.xlsx");
			InputStream inputStream = resource.getInputStream();

			// Tạo file tạm
			File tempFile = new File(System.getProperty("java.io.tmpdir"), CommonUtils.formatFileName());

			// Copy nội dung resource vào file tạm
			try (OutputStream outStream = new FileOutputStream(tempFile)) {
				inputStream.transferTo(outStream);
			}

			fileId = googleDriveService.uploadFile(tempFile);
			return fileId;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
