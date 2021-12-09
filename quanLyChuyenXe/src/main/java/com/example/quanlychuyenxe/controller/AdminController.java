package com.example.quanlychuyenxe.controller;


import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.services.LuongCoBanService;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.model.XeKhach;

import com.example.quanlychuyenxe.services.KhachHangService;
import com.example.quanlychuyenxe.services.TaiXeService;


import com.example.quanlychuyenxe.services.TuyenXeService;
import com.example.quanlychuyenxe.services.XeKhachService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private TuyenXeService tuyenXeService;
    private LuongCoBanService luongCoBanService;
    private XeKhachService xeKhachService;
    private KhachHangService khachHangService;
    private TaiXeService taiXeService;

   //tuyen xe
    @PostMapping("/addTuyenXe")
    public ResponseEntity addTuyenXe(@RequestBody TuyenXe tuyenXe) {
        return ResponseEntity.ok(tuyenXeService.create(tuyenXe).build());
    }

    @DeleteMapping("/deleteTuyenXe/{idTuyenXe}")
    public ResponseEntity deleteTuyenXe(@PathVariable("idTuyenXe") String idTuyenXe) {
        return ResponseEntity.ok().body(tuyenXeService.delete(idTuyenXe).build());
    }

    @PutMapping("/updateTuyenXe/{idTuyenXe}")
    public ResponseEntity updateTuyenXe(@PathVariable("idTuyenXe") String idTuyenXe, @RequestBody TuyenXe tuyenXe) {
        Integer id = Integer.parseInt(idTuyenXe);
        if (tuyenXe.getId().equals(id)) {
            return ResponseEntity.ok().body(tuyenXeService.update(tuyenXe).build());
        }
        return ResponseEntity.ok().body(ResponseBuilder.ok(200, "Cập nhật thất bại"));
    }

    // Khach Hang
    @PostMapping("/addKhachHang")
    public ResponseEntity addKhachHang(@RequestBody KhachHang khachHang) {
        return ResponseEntity.ok().body(khachHangService.create(khachHang).build());
    }

    @DeleteMapping("/deleteKhachHang/{cmtKhachHang}")
    public ResponseEntity deleteKhachHang(@PathVariable("cmtKhachHang") String cmtKhachHang) {
        return ResponseEntity.ok().body(khachHangService.delete(cmtKhachHang).build());
    }

    @GetMapping("/searchKhachHang")
    public ResponseEntity searchKhachHangByName(@RequestParam("tenKhachHang") String tenKhachHang) {
        return ResponseEntity.ok().body(khachHangService.searchByName(tenKhachHang).build());
    }

    @GetMapping("/showKhachHang/{cmtKhachHang}")
    public ResponseEntity searchKhachHangByID(@PathVariable("cmtKhachHang") String cmtKhachHang) {
        return ResponseEntity.ok().body(khachHangService.searchById(cmtKhachHang).build());
    }

    @PutMapping("/updateKhachHang/{cmtKhachHang}")
    public ResponseEntity updateKhachHang(@PathVariable("cmtKhachHang") String cmtKhachHang, @RequestBody KhachHang khachHang) {
        if (khachHang.getCmtKhachHang().equals(cmtKhachHang)) {
            return ResponseEntity.ok().body(khachHangService.create(khachHang).build());
        }
        throw new IllegalStateException("Error");
    }

    // Tai Xe
    @PostMapping("/addTaiXe")
    public ResponseEntity addTaiXe(@RequestBody TaiXe taiXe) {
        return ResponseEntity.ok().body(taiXeService.create(taiXe).build());
    }

    @DeleteMapping("/deleteTaiXe/{cmtTaiXe}")
    public ResponseEntity deleteTaiXe(@PathVariable("cmtTaiXe") String cmtTaiXe) {
        return ResponseEntity.ok().body(taiXeService.delete(cmtTaiXe).build());
    }

    @GetMapping("/searchTaiXe")
    public ResponseEntity searchTaiXeByName(@RequestParam("tenTaiXe") String tenTaiXe) {
        return ResponseEntity.ok().body(taiXeService.searchByName(tenTaiXe).build());
    }

    @GetMapping("/showTaiXe/{cmtTaiXe}")
    public ResponseEntity searchTaiXeByID(@PathVariable("cmtTaiXe") String cmtTaiXe) {
        return ResponseEntity.ok().body(taiXeService.searchById(cmtTaiXe).build());
    }

    @PutMapping("/updateTaiXe/{cmtTaiXe}")
    public ResponseEntity updateTaiXe(@PathVariable("cmtTaiXe") String cmtTaiXe, @RequestBody TaiXe taiXe) {
        if (taiXe.getCmtTaiXe().equals(cmtTaiXe)) {
            return ResponseEntity.ok().body(taiXeService.create(taiXe).build());
        }
        throw new IllegalStateException("Error");
    }

    //XE KHACH
    @PostMapping("/addXeKhach")
    public ResponseEntity addXeKhach(@RequestBody XeKhach xeKhach) {
        return ResponseEntity.ok().body(xeKhachService.create(xeKhach).build());
    }

    @DeleteMapping("/deleteXeKhach/{bienSo}")
    public ResponseEntity deleteXeKhach(@PathVariable("bienSo") String bienSo) {
        return ResponseEntity.ok().body(xeKhachService.delete(bienSo).build());
    }

    @GetMapping("/searchXeKhach")
    public ResponseEntity searchXeKhachByID(@RequestParam("bienSo") String bienSo) {
        return ResponseEntity.ok().body(xeKhachService.searchById(bienSo).build());
    }

    @GetMapping("/showXeKhach/{id}")
    public ResponseEntity searchXeKhach(@PathVariable("id") String id,
                                        @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(xeKhachService.showXeKhach(id, pageable).build());
    }

    @PutMapping("/updateXeKhach/{bienSo}")
    public ResponseEntity updateKhachHang(@PathVariable("bienSo") String bienSo, @RequestBody XeKhach xeKhach) {
        if (xeKhach.getBienSo().equals(bienSo)) {
            return ResponseEntity.ok().body(xeKhachService.create(xeKhach).build());
        }
        throw new IllegalStateException("Error");
    }


}