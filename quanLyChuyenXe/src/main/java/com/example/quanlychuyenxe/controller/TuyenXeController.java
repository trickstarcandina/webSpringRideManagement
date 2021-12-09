package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.services.TuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tuyenxe")
@AllArgsConstructor
public class TuyenXeController {

    private TuyenXeService tuyenXeService;

//    @GetMapping("/searchTuyenXe")
//    public ResponseEntity searchTuyenXeByName(@RequestParam("diemDau") String diemDau, @RequestParam("dieuCuoi") String diemCuoi) {
//        return ResponseEntity.ok().body(tuyenXeService.searchTuyenXeDiemDauDiemCuoi(diemDau, diemCuoi).build());
//    }

//
//    @GetMapping("/showTuyenXe/{idTuyenXe}")
//    public ResponseEntity searchTuyenXeByID(@PathVariable("idTuyenXe") String idTuyenXe,
//                                              @RequestParam int page, @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return ResponseEntity.ok().body(tuyenXeService.showTuyenXe(idTuyenXe, pageable).build());
//    }

    @GetMapping("/searchTuyenXe")
    public ResponseEntity searchTuyenXe(@RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi) {
        return ResponseEntity.ok().body(tuyenXeService.search(diemDau, diemCuoi).build());
    }

    @GetMapping("/showTuyenXeByID/{idTuyenXe}")
    public ResponseEntity searchTuyenXe(@PathVariable("idTuyenXe") Integer idTuyenXe) {
        return ResponseEntity.ok().body(tuyenXeService.showTuyenXeById(idTuyenXe).build());
    }
}
