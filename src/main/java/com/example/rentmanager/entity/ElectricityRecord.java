package com.example.rentmanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "electricity_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectricityRecord extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer startElectric;
	private Integer endElectric;

	@Column(length = 7)
	private String month;

	private Long roomId;

	public ElectricityRecord(Long roomId, Integer startElectric, Integer endElectric, String month) {
		super();
		this.roomId = roomId;
		this.startElectric = startElectric;
		this.endElectric = endElectric;
		this.month = month;
	}
}
