package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.TongLuong;
import com.example.quanlychuyenxe.model.request.TongLuongRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
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
@RequestMapping("taixe")
public class TaiXeController {

    private RestTemplate rest = new RestTemplate();

    private TaiXe taixe;

    public TaiXeController() {
        String username = "taixe2";
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showTaiXe/{username}",
                ResponseBuilder.class, username);
        ObjectMapper objectMapper = new ObjectMapper();
        TaiXe taiXe = objectMapper.convertValue(builder.getData(), TaiXe.class);
        this.taixe = taiXe;
    }

    @GetMapping("")
    private String home(Model model) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/searchTaiXe")
                .queryParam("username", taixe.getUsername())
                .queryParam("status", 0);
        ResponseBuilder responseBuilder = rest.getForObject(urlBuilder.build().encode().toUri(), ResponseBuilder.class);
        List<ChuyenXe> listchuyenxe = (List<ChuyenXe>) responseBuilder.getData();
        model.addAttribute("taixe", taixe);
        model.addAttribute("listchuyenxe", listchuyenxe);
        return "taixe/home";
    }

    @GetMapping("xacnhan/{id}")
    private String xacNhanXong(Model model, @PathVariable("id") Integer id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/updateStatus")
                .queryParam("id", id)
                .queryParam("status", 1);
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange(builder.build().encode().toUri() ,
                HttpMethod.PUT, null, ResponseBuilder.class);
        model.addAttribute("notice", responseEntity.getBody().getMessage());

        // Thêm vào bảng tổng lương
        TongLuongRequest tongLuongRequest = new TongLuongRequest();
        tongLuongRequest.setUsernameTaiXe(taixe.getUsername());
        tongLuongRequest.setChuyenXeId(id);
        ResponseEntity<ResponseBuilder> responseAdd = rest.exchange("http://localhost:8080/api/tongluong/save",
                HttpMethod.POST, new HttpEntity<>(tongLuongRequest, null), ResponseBuilder.class);
        if(responseAdd.getBody().getStatus() == 100) {
            model.addAttribute("noticeError", responseAdd.getBody().getMessage());
        }
        return "redirect:/taixe";
    }

    @GetMapping("chonchuyenxe")
    public String chonChuyenXeHome(Model model) {
        model.addAttribute("taixe", taixe);
        return "taixe/registerRide";
    }

    @GetMapping("chonchuyenxe/search")
    private String searchChuyenXe(Model model, @RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/showChuyenXeChuaChay")
                .queryParam("diemDau", diemDau)
                .queryParam("diemCuoi", diemCuoi);
        ResponseBuilder responseBuilder = rest.getForObject(builder.build().encode().toUri(), ResponseBuilder.class);
        List<ChuyenXe> listchuyenxe = (List<ChuyenXe>) responseBuilder.getData();
        model.addAttribute("taixe", taixe);
        model.addAttribute("listchuyenxe", listchuyenxe);
        return "taixe/registerRide";
    }

    @GetMapping("chonchuyenxe/laixe/{id}")
    private String chonLaiXe(Model model, @PathVariable("id") String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/updateLaiXe")
                .queryParam("id", id)
                .queryParam("username", taixe.getUsername());
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange(builder.build().encode().toUri() ,
                HttpMethod.PUT, null, ResponseBuilder.class);
        model.addAttribute("notice", responseEntity.getBody().getMessage());
        model.addAttribute("taixe", taixe);
        return "taixe/registerRide";
    }

    @GetMapping("chonchuyenxe/phuxe/{id}")
    private String chonPhuXe(Model model, @PathVariable("id") String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/updatePhuXe")
                .queryParam("id", id)
                .queryParam("username", taixe.getUsername());
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange(builder.build().encode().toUri() ,
                HttpMethod.PUT, null, ResponseBuilder.class);
        model.addAttribute("notice", responseEntity.getBody().getMessage());
        model.addAttribute("taixe", taixe);
        return "taixe/registerRide";
    }

    // Chức năng lương
    @GetMapping("xemluong")
    public String showLuong(Model model) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/searchTaiXe")
                .queryParam("username", taixe.getUsername())
                .queryParam("status", 1);
        ResponseBuilder responseBuilder = rest.getForObject(urlBuilder.build().encode().toUri(), ResponseBuilder.class);
        List<ChuyenXe> listchuyenxe = (List<ChuyenXe>) responseBuilder.getData();
        model.addAttribute("taixe", taixe);
        model.addAttribute("listchuyenxe", listchuyenxe);
        return "taixe/showSalary";
    }

    @GetMapping("xemluong/search")
    public String searchLuong(Model model, @RequestParam("thang") Integer thang, @RequestParam("nam") Integer nam) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/tongluong/getLuongTaiXe")
                .queryParam("username", taixe.getUsername())
                .queryParam("thang", thang).queryParam("nam", nam);
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange(builder.build().encode().toUri() ,
                HttpMethod.GET, null, ResponseBuilder.class);

        List<TongLuong> listtongluong = (List<TongLuong>) responseEntity.getBody();

        model.addAttribute("taixe", taixe);
        return "taixe/registerRide";
    }

}