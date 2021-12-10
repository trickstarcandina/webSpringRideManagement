package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.services.ChuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chuyenxe")
@AllArgsConstructor
public class ChuyenXeController {
    private ChuyenXeService chuyenXeService;

    @GetMapping("/searchChuyenXe")
    public ResponseEntity searchChuyenXe(@RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi) {
        return ResponseEntity.ok().body(chuyenXeService.searchByTuyenXe(diemDau, diemCuoi).build());
    }

    @GetMapping("/searchTaiXe")
    public  ResponseEntity searchTaiXe(@RequestParam("username") String username, @RequestParam("status") Integer status) {
        return ResponseEntity.ok().body(chuyenXeService.searchLaiXeByStatus(username, status).build());
    }

    @PutMapping("/updateStatus")
    public ResponseEntity updateStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
        return ResponseEntity.ok().body(chuyenXeService.updateStatus(id, status).build());
    }

    @GetMapping("/showChuyenXeChuaChay")
    public ResponseEntity findChuyenXeChuaChay(@RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi) {
        return ResponseEntity.ok().body(chuyenXeService.findChuyenXeThieuTaiXe(diemDau, diemCuoi).build());
    }

    @PutMapping("/updateLaiXe")
    public ResponseEntity updateLaiXe(@RequestParam("id") Integer id, @RequestParam("username") String username) {
        return ResponseEntity.ok().body(chuyenXeService.updateLaiXeById(id, username).build());
    }

    @PutMapping("/updatePhuXe")
    public ResponseEntity updatePhuXe(@RequestParam("id") Integer id, @RequestParam("username") String username) {
        return ResponseEntity.ok().body(chuyenXeService.updatePhuXeById(id, username).build());
    }

    @GetMapping("/khachhang/search")
    public ResponseEntity searchChuyenXe(@RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi
            , @RequestParam("status") Integer status) {
        return ResponseEntity.ok().body(chuyenXeService.findChuyenXeByStatus(diemDau, diemCuoi, status).build());
    }
}