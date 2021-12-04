package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.services.TuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private TuyenXeService tuyenXeService;

    //tuyen xe
    @PostMapping("/addTuyenXe")
    public ResponseEntity addTuyenXe(@RequestBody TuyenXe tuyenXe) {
        return ResponseEntity.ok(tuyenXeService.create(tuyenXe).build());
    }

    @DeleteMapping("/deleteTuyenXe/{idTuyenXe}")
    public ResponseEntity deleteKhachHang(@PathVariable("idTuyenXe") String idTuyenXe) {
        return ResponseEntity.ok().body(tuyenXeService.delete(idTuyenXe).build());
    }

    @PutMapping("/updateKhachHang/{idTuyenXe}")
    public ResponseEntity updateKhachHang(@PathVariable("idTuyenXe") String idTuyenXe, @RequestBody TuyenXe tuyenXe) {
        if(tuyenXe.getId().equals(idTuyenXe)) {
            return ResponseEntity.ok().body(tuyenXeService.create(tuyenXe).build());
        }
        return ResponseEntity.ok().body(ResponseBuilder.ok(200, "Cập nhật thất bại"));
    }
}
