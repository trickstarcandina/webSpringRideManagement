package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.dto.KhachHangDetailsDTO;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.services.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khachhang")
@AllArgsConstructor
public class KhachHangController {

    private final KhachHangService khachHangService;
    private BCryptPasswordEncoder encoder;

    @GetMapping
    public ResponseEntity getKhachHang() {
        KhachHangDetailsDTO khachHangDetailsDTO = (KhachHangDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(khachHangDetailsDTO.getKhachHang());
    }

    @PostMapping(value = "createNewAccount")
    public ResponseEntity create(@RequestBody KhachHang khachHang) {
        khachHang.setPassword(encoder.encode(khachHang.getPassword()));
        return ResponseEntity.ok(khachHangService.create(khachHang).build());
    }

    @GetMapping("/getChuyenXe/{username}")
    public ResponseEntity getChuyenXe(@PathVariable("username") String username) {
        return ResponseEntity.ok(khachHangService.getKhachHangChuyenXe(username).build());
    }
}
