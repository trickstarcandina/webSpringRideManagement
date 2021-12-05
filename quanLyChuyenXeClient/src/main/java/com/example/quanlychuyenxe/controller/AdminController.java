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
    @GetMapping("customers")
    public String homeCustomer() {
        return "redirect:customers/search?name=";
//        return "admin/customers/search";
    }

    @GetMapping("customers/search")
    public String searchCustomer(ModelMap model, @RequestParam("name") String name) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.getForEntity("http://localhost:8080/api/admin/searchKhachHang?tenKhachHang="
                + name, ResponseBuilder.class);
        List<KhachHang> customers = (List<KhachHang>) responseEntity.getBody().getData();
        model.addAttribute("listkhachhang", customers);
        return "admin/customers/search";
    }

    @GetMapping("customers/add")
    public String addCustomer(Model model) {
        model.addAttribute("khachhang", new KhachHang());
        return "admin/customers/addOrEdit";
    }

    @GetMapping("customers/edit/{cmtKhachHang}")
    public String editCustomer(Model model, @PathVariable("cmtKhachHang") String cmtKhachHang) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showKhachHang/{cmtKhachHang}",
                ResponseBuilder.class, cmtKhachHang);
        ObjectMapper objectMapper = new ObjectMapper();
        KhachHang customer = objectMapper.convertValue(builder.getData(), KhachHang.class);
        customer.setIsEdit(true);
        model.addAttribute("khachhang", customer);
        return "admin/customers/addOrEdit";
    }

    @GetMapping("customers/delete/{cmtKhachHang}")
    public ModelAndView deleteCustomer(ModelMap model, @PathVariable("cmtKhachHang") String cmtKhachHang) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteKhachHang/" + cmtKhachHang,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/customers/search", model);
    }

    @PostMapping("customers/update")
    public String updateCustomer(Model model, @ModelAttribute("khachhang") KhachHang khachHang) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateKhachHang/" + khachHang.getCmtKhachHang(),
                HttpMethod.PUT, new HttpEntity<>(khachHang, null), ResponseBuilder.class);
        String noticeUpdate = "";
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            noticeUpdate = "Cập nhật thành công!";
        } else {
            noticeUpdate = "Cập nhật thất bại!";
        }
        model.addAttribute("noticeUpdate", noticeUpdate);
        return "admin/customers/search";
    }

    @PostMapping("customers/save")
    public String saveCustomer(Model model, @ModelAttribute("khachhang") KhachHang khachHang) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showKhachHang/{cmtKhachHang}",
                ResponseBuilder.class, khachHang.getCmtKhachHang());
        ObjectMapper objectMapper = new ObjectMapper();
        KhachHang customer = objectMapper.convertValue(builder.getData(), KhachHang.class);
        String notice = "";
        if(!ObjectUtils.isEmpty(customer)) {
            notice = "Thẻ căn cước công dân đã tồn tại!";
        } else {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addKhachHang",
                    HttpMethod.POST, new HttpEntity<>(khachHang, null), ResponseBuilder.class);
            notice = "Thành công!";
        }
        model.addAttribute("notice", notice);
        return "admin/customers/addOrEdit";
    }

    // Tài xế
    @GetMapping("drivers")
    public String homeDriver() {
        return "redirect:drivers/search?name=";
    }

    @GetMapping("drivers/search")
    public String searchDriver(ModelMap model, @RequestParam("name") String name) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/searchTaiXe?tenTaiXe=" + name,
                ResponseBuilder.class);
        List<TaiXe> drivers = (List<TaiXe>) builder.getData();
        model.addAttribute("listtaixe", drivers);
        return "admin/drivers/search";
    }

    @GetMapping("drivers/add")
    public String addDriver(Model model) {
        model.addAttribute("taixe", new TaiXe());
        return "admin/drivers/addOrEdit";
    }

    @GetMapping("drivers/edit/{cmtTaiXe}")
    public String editDriver(Model model, @PathVariable("cmtTaiXe") String cmtTaiXe) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showTaiXe/{cmtTaiXe}",
                ResponseBuilder.class, cmtTaiXe);
        ObjectMapper objectMapper = new ObjectMapper();
        TaiXe driver = objectMapper.convertValue(builder.getData(), TaiXe.class);
        driver.setIsEdit(true);
        model.addAttribute("taixe", driver);
        return "admin/drivers/addOrEdit";
    }

    @GetMapping("drivers/delete/{cmtTaiXe}")
    public ModelAndView deleteDriver(ModelMap model, @PathVariable("cmtTaiXe") String cmtTaiXe) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteTaiXe/" + cmtTaiXe,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/drivers/search", model);
    }

    @PostMapping("drivers/update")
    public String updateDriver(Model model, @ModelAttribute("taixe") TaiXe taiXe) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateTaiXe/"
                        + taiXe.getCmtTaiXe(), HttpMethod.PUT, new HttpEntity<>(taiXe, null), ResponseBuilder.class);
        String noticeUpdate = "";
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            noticeUpdate = "Cập nhật thành công!";
        } else {
            noticeUpdate = "Cập nhật thất bại!";
        }
        model.addAttribute("noticeUpdate", noticeUpdate);
        return "admin/drivers/search";
    }

    @PostMapping("drivers/save")
    public String saveDriver(Model model, @Valid @ModelAttribute("taixe") TaiXe taiXe, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/drivers/addOrEdit";
        }
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showTaiXe/{cmtTaiXe}",
                ResponseBuilder.class, taiXe.getCmtTaiXe());
        ObjectMapper objectMapper = new ObjectMapper();
        TaiXe driver = objectMapper.convertValue(builder.getData(), TaiXe.class);
        String notice = "";
        if(!ObjectUtils.isEmpty(driver)) {
            notice = "Thẻ căn cước công dân đã tồn tại!";
        } else {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addTaiXe",
                    HttpMethod.POST, new HttpEntity<>(taiXe, null), ResponseBuilder.class);
            notice = "Thành công!";
        }
        model.addAttribute("notice", notice);
        return "admin/drivers/addOrEdit";
    }
}