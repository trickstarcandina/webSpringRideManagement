package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.services.ChuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chuyenxe")
@AllArgsConstructor
public class ChuyenXeController {
    private ChuyenXeService chuyenXeService;

    @GetMapping("/searchChuyenXe")
    public ResponseEntity searchChuyenXe(@RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi) {
        return ResponseEntity.ok().body(chuyenXeService.searchByTuyenXe(diemDau, diemCuoi).build());
    }
}
