package com.example.rentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CaculateElectricResp {
	private Long roomId;
	private String roomName;
	private double elctricityUsedInLaundry;
	private double totalElectricUsed;
	private double totalMoney;
}
