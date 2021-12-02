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

    @PostMapping("/addTuyenXe")
    public ResponseEntity addTuyenXe(@RequestBody TuyenXe tuyenXe) {
        return ResponseEntity.ok(tuyenXeService.create(tuyenXe).build());
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
    public ResponseEntity searchLuongCoBan(@RequestParam("luong") Long luong){
        return ResponseEntity.ok().body(luongCoBanService.searchByLuong(luong));
    }

    @PutMapping("/updateLuongCoBan/{id}")
    public ResponseEntity updateLuongCoBan(@PathVariable("id") Integer id, @RequestBody LuongCoBan luongCoBan){
        if(luongCoBan.getId().equals(id)){
            return ResponseEntity.ok().body(luongCoBanService.create(luongCoBan).build());
        }
        throw new IllegalStateException("Error");
    }
}
