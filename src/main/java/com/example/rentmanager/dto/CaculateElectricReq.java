package com.example.rentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CaculateElectricReq {

	private double totalMoney;
	private double totalElectric;
	private String month;
//	private List<ElectricityRecordDto> electrics;

}
