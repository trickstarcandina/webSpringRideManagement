package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TaiXe;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("khachhang")
public class KhachHangController {

    private RestTemplate rest = new RestTemplate();

    private KhachHang khachhang;

    public KhachHangController() {
        String username = "khachhang1";
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showKhachHang/{username}",
                ResponseBuilder.class, username);
        ObjectMapper objectMapper = new ObjectMapper();
        KhachHang khachHang = objectMapper.convertValue(builder.getData(), KhachHang.class);
        this.khachhang = khachHang;
    }

    @GetMapping("")
    private String home(Model model) {
        return "khachhang/home";
    }

    @GetMapping("chonchuyenxe")
    private String dangKyChuyenXe(Model model) {
        model.addAttribute("khachhang", khachhang);
        return "khachhang/registerTrip";
    }

    @GetMapping("chonchuyenxe/search")
    private String searchChuyenXe(Model model, @RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/khachhang/search")
                .queryParam("diemDau", diemDau)
                .queryParam("diemCuoi", diemCuoi)
                .queryParam("status", 0);
        ResponseBuilder responseBuilder = rest.getForObject(builder.build().encode().toUri(), ResponseBuilder.class);
        List<ChuyenXe> listchuyenxe = (List<ChuyenXe>) responseBuilder.getData();
        model.addAttribute("khachhang", khachhang);
        model.addAttribute("listchuyenxe", listchuyenxe);
        return "khachhang/registerTrip";
    }

    @GetMapping("chonchuyenxe/dangky/{id}")
    private String dangKyChuyenXe(Model model, @PathVariable("id") String id) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showChuyenXe/{id}", ResponseBuilder.class, id);
        
//        model.addAttribute("notice", responseEntity.getBody().getMessage());
        model.addAttribute("khachhang", khachhang);
        return "taixe/registerRide";
    }
}
