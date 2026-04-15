package com.codingShuttle.SecurityApp.SecurityApplication.services;

import com.codingShuttle.SecurityApp.SecurityApplication.dtos.LoginDTO;
import com.codingShuttle.SecurityApp.SecurityApplication.dtos.LoginResponseDTO;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.SessionEntity;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.User;
import com.codingShuttle.SecurityApp.SecurityApplication.repositories.SessionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    private final SessionEntityRepository sessionEntityRepository;


    public LoginResponseDTO login(LoginDTO loginDTO) {

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );
        User user=(User) authentication.getPrincipal();
        String accesstoken= jwtService.generateAccessToken(user);
        String refreshtoken= jwtService.generateRefreshToken(user);
        jwtService.createorUpdateSession(user, refreshtoken);



        return new LoginResponseDTO(user.getId(),accesstoken,refreshtoken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userid= jwtService.getUserIdFromToken(refreshToken);
        if (!jwtService.isSessionidValid(userid, refreshToken)) {
            throw new AuthenticationException("Session invalid or revoked") {};
        }
        User user=userService.getUserById(userid);
        String accesstoken=jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(),accesstoken,refreshToken);
    }
    public void logout(User user) {
        sessionEntityRepository.deleteByUser(user);
    }
}
