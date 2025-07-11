package com.example.rentmanager.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Slf4j
public class CommonUtils {

	public static String formatFileName() {
		String name = "tien_dien_ngay_" + formatTodayDate() + "_" + randomNumber(4) + ".xlsx";
		return name;
	}

	public static String formatTodayDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
		return LocalDateTime.now().format(formatter);
	}

	public static String randomNumber(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10)); // Generate a digit from 0 to 9
		}
		return sb.toString();
	}

}
