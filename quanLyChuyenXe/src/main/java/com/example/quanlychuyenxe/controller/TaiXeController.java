package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.dto.TaiXeDetailsDTO;
import com.example.quanlychuyenxe.services.KhachHangService;
import com.example.quanlychuyenxe.services.TaiXeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/taixe")
@AllArgsConstructor
public class TaiXeController {

    private final TaiXeService taiXeService;

    @GetMapping
    public ResponseEntity getTaiXe() {
        TaiXeDetailsDTO taiXeDetailsDTO = (TaiXeDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(taiXeDetailsDTO.getTaiXe());
    }

}
