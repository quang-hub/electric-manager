package com.example.rentmanager.repository;

import com.example.rentmanager.dto.LaundryDto;
import com.example.rentmanager.entity.LaundryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaundryRepository extends JpaRepository<LaundryRecord, Long> {

	@Query("""
			    SELECT new com.example.rentmanager.dto.LaundryDto(
			       room.roomName,
			       COUNT(room.roomName)
			    ) FROM LaundryRecord l JOIN Room room ON l.roomId = room.id
			    WHERE YEAR(l.createdAt) = :year AND MONTH(l.createdAt) = :monthNumber
			    GROUP BY room.roomName order by room.roomName asc 
			""")
	List<LaundryDto> findByCreatedAtYearAndCreatedAtMonth(
			@Param("year") int year,
			@Param("monthNumber") int monthNumber
	);

	List<LaundryRecord> findByRoomId(Long roomId);
}
