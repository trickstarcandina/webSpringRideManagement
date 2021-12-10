package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.services.LuongCoBanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/luongcoban")
@AllArgsConstructor
public class LuongCoBanController {
    private LuongCoBanService luongCoBanService;

    @GetMapping("/getluonguser")
    public ResponseEntity getLuongUser(@RequestParam String username) {
        return ResponseEntity.ok().body(luongCoBanService.findLaterLuong(username).build());
    }
}
