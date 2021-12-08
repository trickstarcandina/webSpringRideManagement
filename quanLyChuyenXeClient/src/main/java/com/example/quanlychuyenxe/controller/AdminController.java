package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TaiXe;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.TypeReference;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
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

    @GetMapping("taixe/edit/{cmtTaiXe}")
    public String editTaiXe(Model model, @PathVariable("cmtTaiXe") String cmtTaiXe) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showTaiXe/{cmtTaiXe}",
                ResponseBuilder.class, cmtTaiXe);
        ObjectMapper objectMapper = new ObjectMapper();
        TaiXe taixe = objectMapper.convertValue(builder.getData(), TaiXe.class);
        taixe.setIsEdit(true);
        model.addAttribute("taixe", taixe);
        return "admin/taixe/addOrEdit";
    }

    @GetMapping("taixe/delete/{cmtTaiXe}")
    public ModelAndView deleteTaiXe(ModelMap model, @PathVariable("cmtTaiXe") String cmtTaiXe) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteTaiXe/" + cmtTaiXe,
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
                        + taiXe.getCmtTaiXe(), HttpMethod.PUT, new HttpEntity<>(taiXe, null), ResponseBuilder.class);
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
            return "admin/taixe/addOrEdit";
        }
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showTaiXe/{cmtTaiXe}",
                ResponseBuilder.class, taiXe.getCmtTaiXe());
        ObjectMapper objectMapper = new ObjectMapper();
        TaiXe taixe = objectMapper.convertValue(builder.getData(), TaiXe.class);
        String notice = "";
        if(!ObjectUtils.isEmpty(taixe)) {
            notice = "Thẻ căn cước công dân đã tồn tại!";
        } else {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addTaiXe",
                    HttpMethod.POST, new HttpEntity<>(taiXe, null), ResponseBuilder.class);
            notice = "Thành công!";
        }
        model.addAttribute("notice", notice);
        return "admin/taixe/addOrEdit";
    }
}