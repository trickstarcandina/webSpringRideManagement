package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.XeKhach;
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
    public String updateCustomer(Model model, @Valid @ModelAttribute("khachhang") KhachHang khachHang, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/customers/search";
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
        return "admin/customers/search";
    }

    @PostMapping("customers/save")
    public String saveCustomer(Model model, @Valid @ModelAttribute("khachhang") KhachHang khachHang, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/customers/addOrEdit";
        }
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
    public String updateDriver(Model model, @Valid @ModelAttribute("taixe") TaiXe taiXe, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/drivers/addOrEdit";
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

    //luong co ban
    @GetMapping("luongcoban")
    public String homeSalary() {
        return "redirect:salarys/search?name=";
    }

//    @GetMapping("salarys/search")
//    public String searchSalary(ModelMap model, @RequestParam("name") String name) {
//        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/searchTaiXe?tenTaiXe=" + name,
//                ResponseBuilder.class);
//        List<TaiXe> drivers = (List<TaiXe>) builder.getData();
//        model.addAttribute("listtaixe", drivers);
//        return "admin/drivers/search";
//    }

    @GetMapping("drivers/salarys/add")
    public String addSalary(Model model) {
        model.addAttribute("luongcoban", new LuongCoBan ());
        return "admin/drivers/addOrEdit";
    }

//    @GetMapping("salarys/edit/{id}")
//    public String editSalary(Model model, @PathVariable("id") Integer id) {
//        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showLuongCoBan/{id}",
//                ResponseBuilder.class, id);
//        ObjectMapper objectMapper = new ObjectMapper();
//        LuongCoBan salary = objectMapper.convertValue(builder.getData(), LuongCoBan.class);
////        salary.setIsEdit(true);
//        model.addAttribute("taixe", salary);
//        return "admin/salarys/addOrEdit";
//    }

//    @GetMapping("salarys/delete/{id}")
//    public ModelAndView deleteDriver(ModelMap model, @PathVariable("cmtTaiXe") String cmtTaiXe) {
//        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteTaiXe/" + cmtTaiXe,
//                HttpMethod.DELETE, null, ResponseBuilder.class);
//        if(responseEntity.getStatusCode() == HttpStatus.OK) {
//            model.addAttribute("deleteNotice", "Xóa thành công");
//        }
//        return new ModelAndView("admin/drivers/search", model);
//    }

//    @PostMapping("drivers/salarys/update")
//    public String updateSalary(Model model, @Valid @ModelAttribute("luongcoban") LuongCoBan luongCoBan, Errors errors) {
//        if(errors.hasErrors()) {
//            return "admin/salarys/addOrEdit";
//        }
//        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateLuongCoBan/"
//                + luongCoBan.getCmtTaiXe(), HttpMethod.PUT, new HttpEntity<>(luongCoBan, null), ResponseBuilder.class);
//        String noticeUpdate = "";
//        if(responseEntity.getStatusCode() == HttpStatus.OK) {
//            noticeUpdate = "Cập nhật thành công!";
//        } else {
//            noticeUpdate = "Cập nhật thất bại!";
//        }
//        model.addAttribute("noticeUpdate", noticeUpdate);
//        return "admin/salarys/search";
//    }

//    @PostMapping("drivers/save")
//    public String saveSalary(Model model, @Valid @ModelAttribute("luongcoban") LuongCoBan luongCoBan, Errors errors) {
//        if(errors.hasErrors()) {
//            return "admin/salarys/addOrEdit";
//        }
//        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showLuongCoBan/{Id}",
//                ResponseBuilder.class, luongCoBan.getId ());
//        ObjectMapper objectMapper = new ObjectMapper();
//        TaiXe driver = objectMapper.convertValue(builder.getData(), TaiXe.class);
//        String notice = "";
//        if(!ObjectUtils.isEmpty(driver)) {
//            notice = "ID đã tồn tại!";
//        } else {
//            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addLuongCoBan",
//                    HttpMethod.POST, new HttpEntity<>(luongCoBan, null), ResponseBuilder.class);
//            notice = "Thành công!";
//        }
//        model.addAttribute("notice", notice);
//        return "admin/salarys/addOrEdit";
//    }

    //XE KHACH
    @GetMapping("coachs")
    public String homeCoach() {
        return "redirect:coachs/search?name=";
    }

    @GetMapping("coachs/search")
    public String searchCoach(ModelMap model, @RequestParam("name") String name) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/searchXeKhach/?tenxekhach=" + name,
                ResponseBuilder.class);
        List<XeKhach> coachs = (List<XeKhach>) builder.getData();
        model.addAttribute("listxekhach", coachs);
        return "admin/coachs/search";
    }

    @GetMapping("coachs/add")
    public String addCoach(Model model) {
        model.addAttribute("xekhach", new XeKhach ());
        return "admin/coachs/addOrEdit";
    }

    @GetMapping("coachs/edit/{bienSo}")
    public String editCoach(Model model, @PathVariable("bienSo") String bienSo) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showXeKhachByID/{bienSo}",
                ResponseBuilder.class, bienSo);
        ObjectMapper objectMapper = new ObjectMapper();
        XeKhach coach = objectMapper.convertValue(builder.getData(), XeKhach.class);
        coach.setIsEdit(true);
        model.addAttribute("xekhach", coach);
        return "admin/coachs/addOrEdit";
    }

    @GetMapping("coachs/delete/{bienSo}")
    public ModelAndView deleteCoach(ModelMap model, @PathVariable("bienSo") String bienSo) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteXeKhach/" + bienSo,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/coachs/search", model);
    }

    @PostMapping("coachs/update")
    public String updateCoach(Model model, @Valid @ModelAttribute("xekhach") XeKhach xeKhach, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/coachs/addOrEdit";
        }
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateXeKhach/"
                + xeKhach.getBienSo (), HttpMethod.PUT, new HttpEntity<>(xeKhach, null), ResponseBuilder.class);
        String noticeUpdate = "";
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            noticeUpdate = "Cập nhật thành công!";
        } else {
            noticeUpdate = "Cập nhật thất bại!";
        }
        model.addAttribute("noticeUpdate", noticeUpdate);
        return "admin/coachs/search";
    }

    @PostMapping("coachs/save")
    public String saveCoach(Model model, @Valid @ModelAttribute("xekhach") XeKhach xeKhach, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/coachs/addOrEdit";
        }
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showXeKhachByID/{bienSo}",
                ResponseBuilder.class, xeKhach.getBienSo ());
        ObjectMapper objectMapper = new ObjectMapper();
        XeKhach driver = objectMapper.convertValue(builder.getData(), XeKhach.class);
        String notice = "";
        if(!ObjectUtils.isEmpty(driver)) {
            notice = "Biển số đã tồn tại!";
        } else {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addXeKhach",
                    HttpMethod.POST, new HttpEntity<>(xeKhach, null), ResponseBuilder.class);
            notice = "Thành công!";
        }
        model.addAttribute("notice", notice);
        return "admin/coachs/addOrEdit";
    }
}