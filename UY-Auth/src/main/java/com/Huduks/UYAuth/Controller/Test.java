package com.Huduks.UYAuth.Controller;

import com.Huduks.UYAuth.DTO.OTPDTO;
import com.Huduks.UYAuth.Utility.OTPUtility;

public class Test {
	public static void main(String[] args) {
		OTPUtility util = new OTPUtility(5, 8);
		
		String message = "utalmighty#dbfs/comm jfkbdgjdfbgklsngsl";
		OTPDTO dto = util.getOTP(message);
		System.out.println(dto.getOtp() + ">> valid for " + dto.getValidtill());
		System.out.println(util.verifyOtp(message, "74591229"));
		//1:40  > 1:40-1:45
		//1:34 -> 1:30-35
		// 1:36 -> 35-40
		// 1-31-> 1:30-1:35
		// 38 -> 35-40 // 74591229
		// 40-45 ->
		//45-50
	}

}
