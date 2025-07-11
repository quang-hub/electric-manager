package com.example.rentmanager.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
	private int code;
	private String message;
	private T data;

}
