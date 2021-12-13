package com.example.quanlychuyenxe.controller;


import com.example.quanlychuyenxe.dto.AuthenticationRequest;
import com.example.quanlychuyenxe.model.*;
import com.example.quanlychuyenxe.model.request.ChuyenXeRequest;
import com.example.quanlychuyenxe.model.request.LuongCoBanRequest;
import com.example.quanlychuyenxe.services.*;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.TuyenXe;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    private TongLuongService tongLuongService;
    private AdminService adminService;

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
        Integer id = Integer.parseInt(idTuyenXe);
        if (tuyenXe.getId().equals(id)) {
            return ResponseEntity.ok().body(tuyenXeService.update(tuyenXe).build());
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
            khachHang.setPassword(encoder.encode(khachHang.getPassword()));
            return ResponseEntity.ok().body(khachHangService.update(khachHang).build());
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
            taiXe.setPassword(encoder.encode(taiXe.getPassword()));
            return ResponseEntity.ok().body(taiXeService.update(taiXe).build());
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
    public ResponseEntity searchXeKhachByTen(@RequestParam("tenxekhach") String tenxekhach) {
        return ResponseEntity.ok().body(xeKhachService.searchByName(tenxekhach).build());
    }

//    @GetMapping("/showXeKhach/{id}")
//    public ResponseEntity searchXeKhach(@PathVariable("id") String id,
//                                        @RequestParam int page, @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return ResponseEntity.ok().body(xeKhachService.showXeKhach(id, pageable).build());
//    }

    @GetMapping("/showXeKhachByID/{bienSo}")
    public ResponseEntity showXeKhach(@PathVariable("bienSo") String bienSo) {
        return ResponseEntity.ok().body(xeKhachService.searchById(bienSo).build());
    }

    @PutMapping("/updateXeKhach/{bienSo}")
    public ResponseEntity updateXeKhach(@PathVariable("bienSo") String bienSo, @RequestBody XeKhach xeKhach) {
        if (xeKhach.getBienSo().equals(bienSo)) {
            return ResponseEntity.ok().body(xeKhachService.create(xeKhach).build());
        }
        throw new IllegalStateException("Error");
    }

    // luong co ban
    @PostMapping("/addLuongCoBan")
    public ResponseEntity addLuongCoBan(@RequestBody LuongCoBanRequest luongCoBanRequest) {
        return ResponseEntity.ok().body(luongCoBanService.create(luongCoBanRequest).build());
    }

    @GetMapping("/showLuongCoBan")
    public ResponseEntity searchLuongCoBanById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok().body(luongCoBanService.searchById(id));
    }

    @PutMapping("/updateLuongCoBan/{id}")
    public ResponseEntity updateLuongCoBan(@PathVariable("id") Integer id, @RequestBody LuongCoBanRequest luongCoBanRequest) {
        if (luongCoBanRequest.getId().equals(id)) {
            return ResponseEntity.ok().body(luongCoBanService.create(luongCoBanRequest).build());
        }
        throw new IllegalStateException("Error");
    }

    @GetMapping("/luongcoban/findByTaiXe")
    public ResponseEntity findByTaiXe(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(luongCoBanService.findLuongByTaiXe(username).build());
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

    // Thong ke tong luong
    @GetMapping("/thongke/luongtaixe")
    public ResponseEntity thongkeTongLuong(@RequestParam("thang") Integer thang, @RequestParam("nam") Integer nam) {
        return ResponseEntity.ok().body(tongLuongService.getAllTongLuongByDate(thang, nam).build());
    }

    @GetMapping("/thongke/chuyenxe")
    public ResponseEntity thongkeChuyenXe() {
        return ResponseEntity.ok().body(chuyenXeService.thongkeChuyenXe().build());
    }

    @GetMapping("/thongke/taixe")
    public ResponseEntity thongkeTaiXe() {
        return ResponseEntity.ok().body(chuyenXeService.thongkeTaiXe().build());
    }

    //auth
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok().body(adminService.findUser(authenticationRequest.getUsername(), authenticationRequest.getPassword()).build());
    }

    @PostMapping("/authen")
    public ResponseEntity authen(@RequestBody AuthenticationRequest authenticationRequest, @RequestParam("code") String code) throws IOException {
        return ResponseEntity.ok().body(adminService.authen(authenticationRequest.getUsername(), authenticationRequest.getPassword(), code).build());
    }

    @PostMapping("/showQRCode")
    public ResponseEntity showQRCode(@RequestParam("displayName") String displayName) {
        return ResponseEntity.ok().body(adminService.showQRCode(displayName).build());
    }
}