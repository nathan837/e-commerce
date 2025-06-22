package com.nathdev.e_commerce.controller;


import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.naming.AuthenticationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nathdev.e_commerce.request.LoginRequest;
import com.nathdev.e_commerce.response.ApiResponse;
import com.nathdev.e_commerce.response.JwtResponse;
import com.nathdev.e_commerce.security.jwt.JwtUtils;
import com.nathdev.e_commerce.security.user.ShopUserDetail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
@PostMapping("/login")
    public ResponseEntity<ApiResponse> login (@Valid @RequestBody LoginRequest loginRequest) {
        try {
             Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtUtils.generateTokenForUser(authentication); 
                    ShopUserDetail userDetails = (ShopUserDetail) authentication.getPrincipal();
                    JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
                    return ResponseEntity.ok(new ApiResponse("Login Success", jwtResponse));
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse("Invalid Email or Password", e.getMessage()));
        }
    }
}
