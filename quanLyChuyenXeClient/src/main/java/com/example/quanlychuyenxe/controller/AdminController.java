package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.request.LuongTrongThangRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {

    private RestTemplate rest = new RestTemplate();

    // Khách hàng
    @GetMapping("khachhang")
    public String homeKhachHang() {
        return "redirect:khachhang/search?name=";
//        return "admin/khachhang/search";
    }

    @GetMapping("khachhang/search")
    public String searchKhachHang(ModelMap model, @RequestParam("name") String name) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.getForEntity("http://localhost:8080/api/admin/searchKhachHang?tenKhachHang="
                + name, ResponseBuilder.class);
        List<KhachHang> listkhachhang = (List<KhachHang>) responseEntity.getBody().getData();
        model.addAttribute("listkhachhang", listkhachhang);
        return "admin/khachhang/search";
    }

    @GetMapping("khachhang/add")
    public String addKhachHang(Model model) {
        model.addAttribute("khachhang", new KhachHang());
        return "admin/khachhang/addOrEdit";
    }

    @GetMapping("khachhang/edit/{cmtKhachHang}")
    public String editKhachHang(Model model, @PathVariable("cmtKhachHang") String cmtKhachHang) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showKhachHang/{cmtKhachHang}",
                ResponseBuilder.class, cmtKhachHang);
        ObjectMapper objectMapper = new ObjectMapper();
        KhachHang khachhang = objectMapper.convertValue(builder.getData(), KhachHang.class);
        khachhang.setIsEdit(true);
        model.addAttribute("khachhang", khachhang);
        return "admin/khachhang/addOrEdit";
    }

    @GetMapping("khachhang/delete/{cmtKhachHang}")
    public ModelAndView deleteKhachHang(ModelMap model, @PathVariable("cmtKhachHang") String cmtKhachHang) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteKhachHang/" + cmtKhachHang,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/khachhang/search", model);
    }

    @PostMapping("khachhang/update")
    public String updateKhachHang(Model model, @Valid @ModelAttribute("khachhang") KhachHang khachHang, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/khachhang/search";
        }
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateKhachHang/" + khachHang.getCmtKhachHang(),
                HttpMethod.PUT, new HttpEntity<>(khachHang, null), ResponseBuilder.class);
        String noticeUpdate = "";
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            noticeUpdate = "Cập nhật thành công!";
        } else {
            noticeUpdate = "Cập nhật thất bại!";
        }
        model.addAttribute("noticeUpdate", noticeUpdate);
        return "admin/khachhang/search";
    }

    @PostMapping("khachhang/save")
    public String saveKhachHang(Model model, @Valid @ModelAttribute("khachhang") KhachHang khachHang, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/khachhang/addOrEdit";
        }
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showKhachHang/{cmtKhachHang}",
                ResponseBuilder.class, khachHang.getCmtKhachHang());
        ObjectMapper objectMapper = new ObjectMapper();
        KhachHang khachhang = objectMapper.convertValue(builder.getData(), KhachHang.class);
        String notice = "";
        if(!ObjectUtils.isEmpty(khachhang)) {
            notice = "Thẻ căn cước công dân đã tồn tại!";
        } else {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addKhachHang",
                    HttpMethod.POST, new HttpEntity<>(khachHang, null), ResponseBuilder.class);
            notice = "Thành công!";
        }
        model.addAttribute("notice", notice);
        return "admin/khachhang/addOrEdit";
    }

    // Tài xế
    @GetMapping("taixe")
    public String homeTaiXe() {
        return "redirect:taixe/search?name=";
    }

    @GetMapping("taixe/search")
    public String searchTaiXe(ModelMap model, @RequestParam("name") String name) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/searchTaiXe?tenTaiXe=" + name,
                ResponseBuilder.class);
        List<TaiXe> listtaixe = (List<TaiXe>) builder.getData();
        model.addAttribute("listtaixe", listtaixe);
        return "admin/taixe/search";
    }

    @GetMapping("taixe/add")
    public String addTaiXe(Model model) {
        model.addAttribute("taixe", new TaiXe());
        return "admin/taixe/addOrEdit";
    }

    @GetMapping("taixe/edit/{username}")
    public String editTaiXe(Model model, @PathVariable("username") String username) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showTaiXe/{username}",
                ResponseBuilder.class, username);
        ObjectMapper objectMapper = new ObjectMapper();
        TaiXe taixe = objectMapper.convertValue(builder.getData(), TaiXe.class);
        taixe.setIsEdit(true);
        model.addAttribute("taixe", taixe);
        return "admin/taixe/addOrEdit";
    }

    @GetMapping("taixe/delete/{username}")
    public ModelAndView deleteTaiXe(ModelMap model, @PathVariable("username") String username) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteTaiXe/" + username,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/taixe/search", model);
    }

    @PostMapping("taixe/update")
    public String updateTaiXe(Model model, @Valid @ModelAttribute("taixe") TaiXe taiXe, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/taixe/addOrEdit";
        }
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateTaiXe/"
                        + taiXe.getUsername(), HttpMethod.PUT, new HttpEntity<>(taiXe, null), ResponseBuilder.class);
        String noticeUpdate = "";
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            noticeUpdate = "Cập nhật thành công!";
        } else {
            noticeUpdate = "Cập nhật thất bại!";
        }
        model.addAttribute("noticeUpdate", noticeUpdate);
        return "admin/taixe/search";
    }

    @PostMapping("taixe/save")
    public String saveTaiXe(Model model, @Valid @ModelAttribute("taixe") TaiXe taiXe, Errors errors) {
        if(errors.hasErrors()) {
            model.addAttribute("noticeDanger", "Lỗi!!!");
            return "admin/taixe/addOrEdit";
        }
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addTaiXe",
                HttpMethod.POST, new HttpEntity<>(taiXe, null), ResponseBuilder.class);
        if(responseEntity.getBody().getStatus() != 200) {
            model.addAttribute("noticeDanger", responseEntity.getBody().getMessage());
        } else {
            model.addAttribute("noticeSuccess", "Thành công!!!");
        }
        return "admin/taixe/addOrEdit";
    }

    // Thống kê
    @GetMapping("thongke/luong")
    public String homeTKLuong() {
        return "admin/thongke/salaryDriver";
    }

    @GetMapping("thongke/luong/search")
    public String searchLuong(Model model, @RequestParam("thang") Integer thang, @RequestParam("nam") Integer nam) {
        if (thang < 1 || nam < 2000 || thang > 12 || nam > 2030) {
            model.addAttribute("dangerNotice", "Nhập sai tháng và năm");
        } else {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/admin/thongke/luongtaixe")
                    .queryParam("thang", thang)
                    .queryParam("nam", nam);
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange(builder.build().encode().toUri() ,
                    HttpMethod.GET, null, ResponseBuilder.class);
            if(responseEntity.getBody().getStatus() != 200) {
                model.addAttribute("dangerNotice", "Không có dữ liệu");
            } else {
                ObjectMapper mapper = new ObjectMapper();
                List<LuongTrongThangRequest> listluong = mapper.convertValue(responseEntity.getBody().getData(),
                        new TypeReference<List<LuongTrongThangRequest>>() { });
                model.addAttribute("listluong", listluong);
            }
        }

        return "admin/thongke/salaryDriver";
    }
}