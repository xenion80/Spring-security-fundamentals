package com.codingShuttle.SecurityApp.SecurityApplication.controllers;

import com.codingShuttle.SecurityApp.SecurityApplication.dtos.LoginDTO;
import com.codingShuttle.SecurityApp.SecurityApplication.dtos.LoginResponseDTO;
import com.codingShuttle.SecurityApp.SecurityApplication.dtos.SignUpDto;
import com.codingShuttle.SecurityApp.SecurityApplication.dtos.UserDTO;
import com.codingShuttle.SecurityApp.SecurityApplication.services.AuthService;
import com.codingShuttle.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> SignUp(@RequestBody SignUpDto signUpDto){
        UserDTO userDTO=userService.signUp(signUpDto);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
       LoginResponseDTO loginResponseDTO =authService.login(loginDTO);
        Cookie cookie=new Cookie("refreshtoken", loginResponseDTO.getRefreshtoken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        String refrehToken=Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshtoken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()-> new AuthenticationServiceException("Refresh token ot found inside the cookies"));
        LoginResponseDTO loginResponseDTO=authService.refreshToken(refrehToken);
        return ResponseEntity.ok(loginResponseDTO);

    }
}
