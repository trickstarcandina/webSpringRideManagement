package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.services.KhachHangService;
import com.example.quanlychuyenxe.services.TuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private TuyenXeService tuyenXeService;
    private KhachHangService khachHangService;

    @PostMapping("/addTuyenXe")
    public ResponseEntity addTuyenXe(@RequestBody TuyenXe tuyenXe) {
        return ResponseEntity.ok(tuyenXeService.create(tuyenXe).build());
    }

    @PostMapping("/addKhachHang")
    public ResponseEntity addKhachHang(@RequestBody KhachHang khachHang) {
        return ResponseEntity.ok().body(khachHangService.create(khachHang).build());
    }

    @GetMapping("/deleteKhachHang/{cmtKhachHang}")
    public ResponseEntity deleteKhachHang(@PathVariable("cmtKhachHang") String cmtKhachHang) {
        return ResponseEntity.ok().body(khachHangService.delete(cmtKhachHang).build());
    }

    @GetMapping("/searchKhachHang")
    public ResponseEntity searchKhachHangByName(@RequestParam("ten") String ten) {
        return ResponseEntity.ok().body(khachHangService.searchByName(ten).build());
    }

    @GetMapping("/editKhachHang/{cmtKhachHang}")
    public ResponseEntity searchKhachHangByID(@PathVariable("cmtKhachHang") String cmtKhachHang) {
        return ResponseEntity.ok().body(khachHangService.searchById(cmtKhachHang).build());
    }
}
