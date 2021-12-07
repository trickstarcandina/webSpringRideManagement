package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.dto.AuthenticationRequest;
import com.example.quanlychuyenxe.dto.UserDetailsDTO;
import com.example.quanlychuyenxe.services.KhachHangService;
import com.example.quanlychuyenxe.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenController {

    private AuthenticationManager authenticationManager;
    private KhachHangService khachHangService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username " + request.getUsername());
        }
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) khachHangService.loadUserByUsername(request.getUsername());
        String jwt = JwtUtils.generateToken(userDetailsDTO);
        return ResponseEntity.ok(jwt);
    }
}