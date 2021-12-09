package com.example.quanlychuyenxe.controller;


import com.example.quanlychuyenxe.model.*;
import com.example.quanlychuyenxe.model.request.ChuyenXeRequest;
import com.example.quanlychuyenxe.services.*;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.TuyenXe;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private ChuyenXeService chuyenXeService;

    private BCryptPasswordEncoder encoder;

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
        if (tuyenXe.getId().equals(idTuyenXe)) {
            return ResponseEntity.ok().body(tuyenXeService.create(tuyenXe).build());
        }
        return ResponseEntity.ok().body(ResponseBuilder.ok(200, "Cập nhật thất bại"));
    }

    // Khach Hang
    @PostMapping("/addKhachHang")
    public ResponseEntity addKhachHang(@RequestBody KhachHang khachHang) {
        khachHang.setPassword(encoder.encode(khachHang.getPassword()));
        return ResponseEntity.ok().body(khachHangService.create(khachHang).build());
    }

    @DeleteMapping("/deleteKhachHang/{username}")
    public ResponseEntity deleteKhachHang(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(khachHangService.delete(username).build());
    }

    @GetMapping("/searchKhachHang")
    public ResponseEntity searchKhachHangByName(@RequestParam("tenKhachHang") String tenKhachHang) {
        return ResponseEntity.ok().body(khachHangService.searchByName(tenKhachHang).build());
    }

    @GetMapping("/showKhachHang/{username}")
    public ResponseEntity searchKhachHangByID(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(khachHangService.searchById(username).build());
    }

    @PutMapping("/updateKhachHang/{username}")
    public ResponseEntity updateKhachHang(@PathVariable("username") String username, @RequestBody KhachHang khachHang) {
        if (khachHang.getUsername().equals(username)) {
            return ResponseEntity.ok().body(khachHangService.create(khachHang).build());
        }
        throw new IllegalStateException("Error");
    }

    // Tai Xe
    @PostMapping("/addTaiXe")
    public ResponseEntity addTaiXe(@RequestBody TaiXe taiXe) {
        taiXe.setPassword(encoder.encode(taiXe.getPassword()));
        return ResponseEntity.ok().body(taiXeService.create(taiXe).build());
    }

    @DeleteMapping("/deleteTaiXe/{username}")
    public ResponseEntity deleteTaiXe(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(taiXeService.delete(username).build());
    }

    @GetMapping("/searchTaiXe")
    public ResponseEntity searchTaiXeByName(@RequestParam("tenTaiXe") String tenTaiXe) {
        return ResponseEntity.ok().body(taiXeService.searchByName(tenTaiXe).build());
    }

    @GetMapping("/showTaiXe/{username}")
    public ResponseEntity searchTaiXeByID(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(taiXeService.searchById(username).build());
    }

    @PutMapping("/updateTaiXe/{username}")
    public ResponseEntity updateTaiXe(@PathVariable("username") String username, @RequestBody TaiXe taiXe) {
        if (taiXe.getUsername().equals(username)) {
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

    @GetMapping("/showXeKhachByID/{bienSo}")
    public ResponseEntity showXeKhach(@PathVariable("bienSo") String bienSo) {
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

    @GetMapping("/searchXeKhach")
    public ResponseEntity searchXeKhachByTen(@RequestParam("tenxekhach") String tenxekhach) {
        return ResponseEntity.ok().body(xeKhachService.searchByName(tenxekhach).build());
    }

    // luong co ban

    @PostMapping("/addLuongCoBan")
    public ResponseEntity addLuongCoBan(@RequestBody LuongCoBan luongCoBan) {
        return ResponseEntity.ok().body(luongCoBanService.create(luongCoBan).build());
    }

    @DeleteMapping("/deleteLuongCoBan/{id}")
    public ResponseEntity deleteLuongCoBan(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(luongCoBanService.delete(id).build());
    }

    @GetMapping("/searchLuongCoBan")
    public ResponseEntity searchLuongCoBanByLuong(@RequestParam("luong") Long luong) {
        return ResponseEntity.ok().body(luongCoBanService.searchByLuong(luong));
    }

    @GetMapping("/showLuongCoBan")
    public ResponseEntity searchLuongCoBanById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok().body(luongCoBanService.searchById(id));
    }

    @PutMapping("/updateLuongCoBan/{id}")
    public ResponseEntity updateLuongCoBan(@PathVariable("id") Integer id, @RequestBody LuongCoBan luongCoBan) {
        if (luongCoBan.getId().equals(id)) {
            return ResponseEntity.ok().body(luongCoBanService.create(luongCoBan).build());
        }
        throw new IllegalStateException("Error");
    }

    // Chuyen Xe
    @PostMapping(value = "/addChuyenXe")
    public ResponseEntity addChuyenXe(@RequestBody ChuyenXeRequest chuyenXeRequest) {

        return ResponseEntity.ok().body(chuyenXeService.create(chuyenXeRequest).build());
    }

    @DeleteMapping("/deleteChuyenXe/{id}")
    public ResponseEntity deleteChuyenXe(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(chuyenXeService.delete(id).build());
    }

    @GetMapping("/allChuyenXe")
    public ResponseEntity allChuyenXe() {
        return ResponseEntity.ok().body(chuyenXeService.getAll().build());
    }

    @GetMapping("/showChuyenXe/{id}")
    public ResponseEntity searchChuyenXeByID(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(chuyenXeService.searchById(id).build());
    }

    @PutMapping("/updateChuyenXe")
    public ResponseEntity updateChuyenXe(@RequestParam("id") Integer id, @RequestBody ChuyenXeRequest chuyenXeRequest) {
        chuyenXeRequest.setId(id);
        return ResponseEntity.ok().body(chuyenXeService.create(chuyenXeRequest).build());
    }
}