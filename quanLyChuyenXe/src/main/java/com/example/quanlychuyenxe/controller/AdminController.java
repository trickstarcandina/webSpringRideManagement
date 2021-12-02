package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.services.LuongCoBanService;
import com.example.quanlychuyenxe.services.TuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private TuyenXeService tuyenXeService;
    private LuongCoBanService luongCoBanService;


    // tuyen xe
    @PostMapping("/addTuyenXe")
    public ResponseEntity addTuyenXe(@RequestBody TuyenXe tuyenXe) {
        return ResponseEntity.ok(tuyenXeService.create(tuyenXe).build());
    }

    @DeleteMapping("/deleteTuyenXe/{id}")
    public ResponseEntity deleteTuyenXe(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(tuyenXeService.delete(id));
    }

    @GetMapping("/searchTuyenXe")
    public ResponseEntity searchTuyenXeByDiemDau(@RequestParam("diemDau") String diemDau){
        return ResponseEntity.ok().body(tuyenXeService.searchByDiemDau(diemDau).build());
    }

    @GetMapping("/showTuyenXe/{id}")
    public ResponseEntity searchTuyenXeById(@RequestParam("id") Integer id){
        return ResponseEntity.ok().body(tuyenXeService.searchById(id).build());
    }

    @PutMapping("/updateTuyenXe/{id}")
    public ResponseEntity updateTuyenXe(@PathVariable("id") Integer id, @RequestBody TuyenXe tuyenXe){
        if(tuyenXe.getId().equals(id)){
            return ResponseEntity.ok().body(tuyenXeService.create(tuyenXe).build());
        }
        throw new IllegalStateException("Error");
    }

    // luong co ban

    @PostMapping("/addLuongCoBan")
    public ResponseEntity addLuongCoBan(@RequestBody LuongCoBan luongCoBan){
        return ResponseEntity.ok().body(luongCoBanService.create(luongCoBan).build());
    }

    @DeleteMapping("/deleteLuongCoBan/{id}")
    public ResponseEntity deleteLuongCoBan(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(luongCoBanService.delete(id).build());
    }

    @GetMapping("/searchLuongCoBan")
    public ResponseEntity searchLuongCoBanByLuong(@RequestParam("luong") Long luong){
        return ResponseEntity.ok().body(luongCoBanService.searchByLuong(luong));
    }

    @GetMapping("/showLuongCoBan")
    public ResponseEntity searchLuongCoBanById(@RequestParam("id") Integer id){
        return ResponseEntity.ok().body(luongCoBanService.searchById(id));
    }

    @PutMapping("/updateLuongCoBan/{id}")
    public ResponseEntity updateLuongCoBan(@PathVariable("id") Integer id, @RequestBody LuongCoBan luongCoBan){
        if(luongCoBan.getId().equals(id)){
            return ResponseEntity.ok().body(luongCoBanService.create(luongCoBan).build());
        }
        throw new IllegalStateException("Error");
    }
}
