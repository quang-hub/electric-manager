package com.example.rentmanager.configuration;

import com.example.rentmanager.entity.ElectricityRecord;
import com.example.rentmanager.entity.Room;
import com.example.rentmanager.repository.ElectricRepository;
import com.example.rentmanager.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final RoomRepository roomRepository;
	private final ElectricRepository electricRepository;
	private String[] roooms = {
			"tầng 2",
			"tầng 3",
			"tầng 4",
			"tầng 5"};

	@Override
	public void run(String... args) {
		for (String room : roooms) {
			if (roomRepository.findByRoomName(room).isEmpty()) {
				roomRepository.save(Room.builder().roomName(room).build());
			} else {
				break;
			}
		}

		if (electricRepository.findAll().isEmpty()) {
			Long i = 1l;
			for (String room : roooms) {
				electricRepository.save(ElectricityRecord.builder()
						.startElectric(100)
						.endElectric(150)
						.roomId(i++)
						.month("7")
						.build());
			}
		}

	}
}
