package com.codingShuttle.SecurityApp.SecurityApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDTO {
    private Long id;
    private String accesstoken;
    private  String refreshtoken;
}
