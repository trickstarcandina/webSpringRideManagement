package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.model.XeKhach;
import com.example.quanlychuyenxe.services.TuyenXeService;
import com.example.quanlychuyenxe.services.XeKhachService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private TuyenXeService tuyenXeService;
    private XeKhachService xeKhachService;

    @PostMapping("/addTuyenXe")
    public ResponseEntity addTuyenXe(@RequestBody TuyenXe tuyenXe) {
        return ResponseEntity.ok(tuyenXeService.create(tuyenXe).build());
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
        return ResponseEntity.ok().body(xeKhachService.searchById (bienSo).build());
    }

    @PutMapping("/updateXeKhach/{bienSo}")
    public ResponseEntity updateKhachHang(@PathVariable("bienSo") String bienSo, @RequestBody XeKhach xeKhach) {
        if(xeKhach.getBienSo ().equals(bienSo)) {
            return ResponseEntity.ok().body(xeKhachService.create(xeKhach).build());
        }
        throw new IllegalStateException("Error");
    }
}

