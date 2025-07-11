package com.example.rentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ElectricityRecordDto {
	private Long roomId;
	private Integer startElectric;
	private Integer endElectric;

}
