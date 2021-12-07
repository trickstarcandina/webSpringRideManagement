package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
import com.example.quanlychuyenxe.dto.UserDetailsDTO;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.services.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class KhachHangController {

    private final KhachHangService khachHangService;
    private BCryptPasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<BaseEntityUser> getUser() {
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userDetailsDTO.getUser());
    }

    @PostMapping(value = "createNewAccount")
    public ResponseEntity create(@RequestBody KhachHang khachHang) {
        khachHang.setPassword(encoder.encode(khachHang.getPassword()));
        return ResponseEntity.ok(khachHangService.create(khachHang).build());
    }

}
