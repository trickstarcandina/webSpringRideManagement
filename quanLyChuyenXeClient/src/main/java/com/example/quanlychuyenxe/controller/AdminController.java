package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.XeKhach;
import com.example.quanlychuyenxe.model.request.LuongTrongThangRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.quanlychuyenxe.model.TuyenXe;
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