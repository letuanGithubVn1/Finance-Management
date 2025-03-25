package com.qlct.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
//	@NotBlank(message = "Email cannot be blank")
//    @Email(message = "Invalid email format")
    private String email;

//    @NotBlank(message = "Password cannot be blank")
//    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

}
