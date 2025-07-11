package com.example.rentmanager.repository;

import com.example.rentmanager.entity.ElectricityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricRepository extends JpaRepository<ElectricityRecord,Long> {
	@Query("""
			    SELECT new com.example.rentmanager.entity.ElectricityRecord(
			        e.roomId,
					e.startElectric,       
					e.endElectric,       
					e.month
			    ) FROM ElectricityRecord e 
			    WHERE YEAR(e.createdAt) = :year AND MONTH(e.createdAt) = :monthNumber
						    AND e.roomId = :roomId
			""")
	List<ElectricityRecord> findByRoomIdAndMonthAndYear(
			@Param("roomId") Long roomId,
			@Param("monthNumber") int monthNumber,
			@Param("year") int year
	);

	ElectricityRecord findByRoomId(Long roomId);
}
