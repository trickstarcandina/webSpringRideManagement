package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.model.request.TongLuongRequest;
import com.example.quanlychuyenxe.services.TongLuongService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tongluong")
@AllArgsConstructor
public class TongLuongController {

    private TongLuongService tongLuongService;

    @PostMapping("/save")
    public ResponseEntity addTongLuong(@RequestBody TongLuongRequest tongLuongRequest) {
        return ResponseEntity.ok().body(tongLuongService.saveTongLuong(tongLuongRequest).build());
    }

    @GetMapping("/getLuongTaiXe")
    public ResponseEntity getTongLuong(@RequestParam("username") String username,
                                       @RequestParam("thang") Integer thang, @RequestParam("nam") Integer nam) {
        return ResponseEntity.ok().body(tongLuongService.getTongLuongByDate(username, thang, nam).build());
    }
}
