package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TaiXe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        ResponseBuilder khachhangBuilder = rest.getForObject("http://localhost:8080/api/khachhang/getChuyenXe/{username}",
                ResponseBuilder.class, khachhang.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        Set<ChuyenXe> chuyenxelist = objectMapper.convertValue(khachhangBuilder.getData(), new TypeReference<Set<ChuyenXe>>() {});
        List<ChuyenXe> listchuyenxe = new ArrayList<>(chuyenxelist);
        model.addAttribute("khachhang", khachhang);
        model.addAttribute("listchuyenxe", listchuyenxe);
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

        ResponseBuilder chuyenxeBuilder = rest.getForObject("http://localhost:8080/api/chuyenxe/allkhachhang/{id}",
                ResponseBuilder.class, id);
//        ResponseBuilder khachhangBuilder = rest.getForObject("http://localhost:8080/api/khachhang/getChuyenXe/{username}",
//                ResponseBuilder.class, khachhang.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
//        Set<KhachHang> khachhanglist = objectMapper.convertValue(chuyenxeBuilder.getData(), new TypeReference<Set<KhachHang>>() {});
//        Set<ChuyenXe> chuyenxelist = objectMapper.convertValue(khachhangBuilder.getData(), new TypeReference<Set<ChuyenXe>>() {});

        ChuyenXe chuyenXe = objectMapper.convertValue(chuyenxeBuilder.getData(), ChuyenXe.class);
        Set<KhachHang> khachHangs = chuyenXe.getKhachHangList();
        if (khachHangs.size() > 0 && khachHangs.contains(khachhang)) {
            model.addAttribute("noticeDanger", "Bạn đã đăng ký chuyến xe này!");
        } else if (chuyenXe.getSoLuongHanhKhach() == chuyenXe.getXeKhach().getSoGhe() - 2) {
            model.addAttribute("noticeDanger", "Đã hết slot!");
        } else {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/updatekhachhang")
                    .queryParam("username", khachhang.getUsername())
                    .queryParam("id", id);
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST, null, ResponseBuilder.class);

            chuyenXe.setSoLuongHanhKhach(chuyenXe.getSoLuongHanhKhach() + 1);
            khachHangs.add(khachhang);
            chuyenXe.setKhachHangList(khachHangs);
            ResponseEntity<ResponseBuilder> update = rest.exchange("http://localhost:8080/api/chuyenxe/update",
                    HttpMethod.PUT, new HttpEntity<>(chuyenXe, null), ResponseBuilder.class);
            model.addAttribute("notice", responseEntity.getBody().getMessage() + "!!!");
        }
        model.addAttribute("khachhang", khachhang);
        return "khachhang/registerTrip";
    }
}
