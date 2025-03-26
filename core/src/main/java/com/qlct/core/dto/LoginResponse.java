package com.qlct.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private String message;
	private String accessToken;
	private String refreshToken;
}
