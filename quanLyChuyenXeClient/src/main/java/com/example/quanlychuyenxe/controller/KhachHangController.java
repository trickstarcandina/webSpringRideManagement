package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.request.KhachHangRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("khachhang")
public class KhachHangController {

    private RestTemplate rest = new RestTemplate();

    private KhachHang khachhang;

    @GetMapping("")
    private String home(Model model, HttpSession session) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization",  session.getAttribute("Token").toString());

        ResponseEntity responseEntity = rest.exchange("http://localhost:8080/api/khachhang", HttpMethod.GET,
                new HttpEntity<>(null,httpHeaders), Map.class);

        ObjectMapper objectMapper = new ObjectMapper();
        khachhang = objectMapper.convertValue(responseEntity.getBody(), KhachHang.class);
        ResponseBuilder khachhangBuilder = rest.getForObject("http://localhost:8080/api/khachhang/getChuyenXe/{username}",
                ResponseBuilder.class, khachhang.getUsername());
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
            model.addAttribute("noticeDanger", "B???n ???? ????ng k?? chuy???n xe n??y!");
        } else if (chuyenXe.getSoLuongHanhKhach() == chuyenXe.getXeKhach().getSoGhe() - 2) {
            model.addAttribute("noticeDanger", "???? h???t slot!");
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


    @GetMapping("formregister")
    private String formregister(Model model) {
        model.addAttribute("khachhangRequest", new KhachHangRequest());
        return "khachhang/formregister";
    }

    @PostMapping("registersuccess")
    private String registersuccess(Model model, @Valid @ModelAttribute("khachhangRequest") KhachHangRequest khachHangRequest) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/khachhang/createNewAccount"
                , HttpMethod.POST, new HttpEntity<>(khachHangRequest, null), ResponseBuilder.class);
        String notice = "";
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            notice = "????ng k?? th??nh c??ng!";
        } else {
            notice = "????ng k?? th???t b???i!";
        }
        return "khachhang/registersuccess";
    }
}
