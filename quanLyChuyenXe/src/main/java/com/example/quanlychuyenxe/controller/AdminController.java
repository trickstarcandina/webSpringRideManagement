package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.services.TuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private TuyenXeService tuyenXeService;

    @PostMapping("/addTuyenXe")
    public ResponseEntity addTuyenXe(@RequestBody TuyenXe tuyenXe) {
        return ResponseEntity.ok(tuyenXeService.create(tuyenXe).build());
    }
}
