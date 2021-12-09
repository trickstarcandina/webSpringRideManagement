package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.dto.AuthenticationRequest;
import com.example.quanlychuyenxe.dto.KhachHangDetailsDTO;
import com.example.quanlychuyenxe.dto.TaiXeDetailsDTO;
import com.example.quanlychuyenxe.services.KhachHangService;
import com.example.quanlychuyenxe.services.TaiXeService;
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
public class AuthenticateController {

    private AuthenticationManager authenticationManager;
    private KhachHangService khachHangService;
    private TaiXeService taiXeService;

    @PostMapping("/authenticateKhachHang")
    public ResponseEntity authenticateKhachHang(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username " + request.getUsername());
        }
        KhachHangDetailsDTO khachHangDetailsDTO = (KhachHangDetailsDTO) khachHangService.loadUserByUsername(request.getUsername());
        String jwt = JwtUtils.generateToken(khachHangDetailsDTO);
        return ResponseEntity.ok(ResponseBuilder.ok(jwt).build());
    }

    @PostMapping("/authenticateTaiXe")
    public ResponseEntity authenticateTaiXe(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }
        catch (BadCredentialsException e) {
            System.out.println(e);
            throw new BadCredentialsException("Invalid username " + request.getUsername());
        }
        TaiXeDetailsDTO taiXeDetailsDTO = (TaiXeDetailsDTO) taiXeService.loadUserByUsername(request.getUsername());
        String jwt = JwtUtils.generateToken(taiXeDetailsDTO);
        return ResponseEntity.ok(ResponseBuilder.ok(jwt).build());
    }
}