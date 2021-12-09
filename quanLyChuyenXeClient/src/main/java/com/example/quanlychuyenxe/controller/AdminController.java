package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.TuyenXe;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {

    private RestTemplate rest = new RestTemplate();



    // Tuyến xe
    @GetMapping("carriageways")
    public String homeCarriageway() {
//        return "redirect:carriageways/search?name=";
        return "admin/carriageways/search";
    }

    @GetMapping("carriageways/search")
    public String searchTuyenXe(Model model, @RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/tuyenxe/searchTuyenXe")
                .queryParam("diemDau", diemDau)
                .queryParam("diemCuoi", diemCuoi);
        ResponseBuilder responseBuilder = rest.getForObject(builder.build().encode().toUri(), ResponseBuilder.class);
        List<TuyenXe> listtuyenxe = (List<TuyenXe>) responseBuilder.getData();
        model.addAttribute("listTuyenXe", listtuyenxe);
        return "admin/carriageways/search";
    }

    @GetMapping("carriageways/add")
    public String addCarriageway(Model model) {
        model.addAttribute("tuyenXe", new TuyenXe());
        return "admin/carriageways/addOrEdit";
    }

    @GetMapping("carriageways/edit/{idTuyenXe}")
    public String editCarriageway(Model model, @PathVariable("idTuyenXe") String idTuyenXe) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/tuyenxe/showTuyenXeByID/{idTuyenXe}",
                ResponseBuilder.class, idTuyenXe);
        ObjectMapper objectMapper = new ObjectMapper();
        TuyenXe carriageway = objectMapper.convertValue(builder.getData(), TuyenXe.class);
        carriageway.setIsEdit(true);
        model.addAttribute("tuyenXe", carriageway);
        return "admin/carriageways/addOrEdit";
    }

    @GetMapping("carriageways/delete/{idTuyenXe}")
    public ModelAndView deleteCarriageway(ModelMap model, @PathVariable("idTuyenXe") String idTuyenXe) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteTuyenXe/" + idTuyenXe,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/carriageways/search", model);
    }

    @PostMapping("carriageways/update")
    public String updateCarriageway(Model model, @Valid @ModelAttribute("tuyenXe") TuyenXe tuyenXe, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/carriageways/search";
        }
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/admin/updateTuyenXe/{idTuyenXe}");
//        Map<String, Integer> maps = new HashMap<>();
//        maps.put("idTuyenXe", tuyenXe.getId());


        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateTuyenXe/" + tuyenXe.getId(),
                HttpMethod.PUT, new HttpEntity<>(tuyenXe, null), ResponseBuilder.class);
        String noticeUpdate = "";
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            noticeUpdate = "Cập nhật thành công!";
        } else {
            noticeUpdate = "Cập nhật thất bại!";
        }
        model.addAttribute("noticeUpdate", noticeUpdate);
        return "admin/carriageways/search";
    }

    @PostMapping("carriageways/save")
    public String saveCarriageway(Model model, @Valid @ModelAttribute("tuyenXe") TuyenXe tuyenXe, Errors errors) {
//        if(errors.hasErrors()) {
//            return "admin/carriageways/addOrEdit";
//        }
//        rest.getForObject("http://localhost:8080/api/tuyenxe/showTuyenXeByID/{idTuyenXe}",
//                ResponseBuilder.class, tuyenXe.getId());
//        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/tuyenxe/showTuyenXeByID/{idTuyenXe}",
//                ResponseBuilder.class, tuyenXe.getId());
//        ObjectMapper objectMapper = new ObjectMapper();
//        TuyenXe carriageway = objectMapper.convertValue(builder.getData(), TuyenXe.class);
//        String notice = "";
//        if(!ObjectUtils.isEmpty(carriageway)) {
//            notice = "ID tuyến xe đã tồn tại!";
//        } else {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addTuyenXe",
                    HttpMethod.POST, new HttpEntity<>(tuyenXe, null), ResponseBuilder.class);
            String notice = "Thành công!";
//        }
        model.addAttribute("notice", notice);
        return "admin/carriageways/addOrEdit";
    }


    // Luong Co Ban

    @GetMapping("salarys")
    public String homeSalarys(){
        return "redirect:salarys/search?salary=";
    }

    @GetMapping("salarys/search")
    public String searchSalary(ModelMap model, @RequestParam("salary") Long salary){
        ResponseEntity<ResponseBuilder> responseEntity = rest.getForEntity("http://localhost:8080/api/admin/searchLuongCoBan?luong="
                + salary, ResponseBuilder.class);
        List<LuongCoBan> salarys = (List<LuongCoBan>) responseEntity.getBody().getData();
        model.addAttribute("listLuongCoBan", salarys);
        return "admin/salarys/search";
    }

    @GetMapping("salarys/add")
    public String addSalary(Model model){
        model.addAttribute("luongCoBan", new LuongCoBan());
        return "admin/salarys/addOrEdit";
    }

    @GetMapping("salarys/edit/{id}")
    public String editSalary(Model model, @PathVariable("id") Integer id) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showLuongCoBan/{id}",
                ResponseBuilder.class, id);
        ObjectMapper objectMapper = new ObjectMapper();
        LuongCoBan salary = objectMapper.convertValue(builder.getData(), LuongCoBan.class);
        salary.setIsEdit(true);
        model.addAttribute("luongCoBan", salary);
        return "admin/salarys/addOrEdit";
    }


    @GetMapping("salarys/delete/{id}")
    public ModelAndView deleteSalary(ModelMap model, @PathVariable("id") Integer id) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteLuongCoBan/" + id,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/salarys/search", model);
    }

    @PostMapping("salarys/update")
    public String updateSalary(Model model, @Valid @ModelAttribute("luongCoBan") LuongCoBan luongCoBan, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/salarys/search";
        }
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateLuongCoBan/" + luongCoBan.getId(),
                HttpMethod.PUT, new HttpEntity<>(luongCoBan, null), ResponseBuilder.class);
        String noticeUpdate = "";
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            noticeUpdate = "Cập nhật thành công!";
        } else {
            noticeUpdate = "Cập nhật thất bại!";
        }
        model.addAttribute("noticeUpdate", noticeUpdate);
        return "admin/salarys/search";
    }

    @PostMapping("salarys/save")
    public String saveSalary(Model model, @Valid @ModelAttribute("luongCoBan") LuongCoBan luongCoBan, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/salarys/addOrEdit";
        }
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showLuongCoBan/{id}",
                ResponseBuilder.class, luongCoBan.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        LuongCoBan salary = objectMapper.convertValue(builder.getData(), LuongCoBan.class);
        String notice = "";
        if(!ObjectUtils.isEmpty(salary)) {
            notice = "Id lương cơ bản đã tồn tại";
        } else {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addLuongCoBan",
                    HttpMethod.POST, new HttpEntity<>(luongCoBan, null), ResponseBuilder.class);
            notice = "Thành công!";
        }
        model.addAttribute("notice", notice);
        return "admin/salarys/addOrEdit";
    }


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
}