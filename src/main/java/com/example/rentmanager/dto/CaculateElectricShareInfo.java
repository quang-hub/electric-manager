package com.example.rentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CaculateElectricShareInfo {
	private double pricePerUnit;
	private double shareElectric;
	private double shareMoney;
	private List<CaculateElectricResp> electricDetails;
}
