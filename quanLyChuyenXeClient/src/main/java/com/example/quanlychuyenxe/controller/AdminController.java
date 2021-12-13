package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.XeKhach;
import com.example.quanlychuyenxe.model.*;
import com.example.quanlychuyenxe.model.request.LuongCoBanRequest;
import com.example.quanlychuyenxe.model.request.LuongTrongThangRequest;
import com.example.quanlychuyenxe.model.request.TKChuyenXeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @GetMapping("khachhang/edit/{username}")
    public String editKhachHang(Model model, @PathVariable("username") String username) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showKhachHang/{cmtKhachHang}",
                ResponseBuilder.class, username);
        ObjectMapper objectMapper = new ObjectMapper();
        KhachHang khachhang = objectMapper.convertValue(builder.getData(), KhachHang.class);
        khachhang.setIsEdit(true);
        model.addAttribute("khachhang", khachhang);
        return "admin/khachhang/addOrEdit";
    }

    @GetMapping("khachhang/delete/{username}")
    public ModelAndView deleteKhachHang(ModelMap model, @PathVariable("username") String username) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteKhachHang/" + username,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/khachhang/search", model);
    }

    @PostMapping("khachhang/update")
    public String updateKhachHang(Model model, @Valid @ModelAttribute("khachhang") KhachHang khachHang, Errors errors) {
        if(errors.hasErrors()) {
            khachHang.setIsEdit(true);
            model.addAttribute("khachhang", khachHang);
            return "admin/khachhang/addOrEdit";
        }

        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateKhachHang/"
                        + khachHang.getUsername(), HttpMethod.PUT, new HttpEntity<>(khachHang, null), ResponseBuilder.class);
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
                ResponseBuilder.class, khachHang.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        KhachHang khachhang = objectMapper.convertValue(builder.getData(), KhachHang.class);
        String notice = "";
        if(!ObjectUtils.isEmpty(khachhang)) {
            notice = "Username đã tồn tại!";
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

    @GetMapping("taixe/addLuong/{username}")
    public String themLuong(Model model, @PathVariable("username") String username) {
        LuongCoBan luongCoBan = new LuongCoBan ();
        TaiXe newTaiXe = new TaiXe();
        newTaiXe.setUsername (username);
        luongCoBan.setTaixe (newTaiXe);
        model.addAttribute ("luongCoBan", luongCoBan);
        return "admin/taixe/editSalary";
    }

    @PostMapping("taixe/saveLuong/{username}")
    public String saveLuongCoBan(Model model, @Valid @ModelAttribute("luongcoban") LuongCoBan luongCoBan, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("noticeDanger", "Lỗi!!!");
            return "admin/taixe/editSalary";
        }
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/taixe/saveLuong/{username}",
                HttpMethod.POST, new HttpEntity<>(luongCoBan, null), ResponseBuilder.class);
        if (responseEntity.getBody().getStatus() != 200) {
            model.addAttribute("noticeDanger", responseEntity.getBody().getMessage());
        } else {
            model.addAttribute("noticeSuccess", "Thành công!!!");
        }
        return "admin/taixe/editSalary";
    }

    // Lương cơ bản
    @GetMapping("taixe/editLuong/{username}")
    public ModelAndView showLuong(ModelMap model, @PathVariable("username") String username) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/luongcoban/findByTaiXe?username="
                        + username, HttpMethod.GET, null, ResponseBuilder.class);
        ObjectMapper objectMapper = new ObjectMapper();
        LuongCoBanRequest luongCoBanRequest = objectMapper.convertValue(responseEntity.getBody().getData(), LuongCoBanRequest.class);
        if(luongCoBanRequest != null) {
            luongCoBanRequest.setIsEdit(true);
            if(luongCoBanRequest.getThangLuong().getDay() != new Date().getDay()
                    || luongCoBanRequest.getThangLuong().getMonth() != new Date().getMonth()
                    || luongCoBanRequest.getThangLuong().getYear() != new Date().getYear()) {
                luongCoBanRequest.setId(null);
            }
        } else {
            luongCoBanRequest = new LuongCoBanRequest();
        }
        luongCoBanRequest.setThangLuong(new Date());
        model.addAttribute("luongcoban", luongCoBanRequest);
        return new ModelAndView("admin/taixe/editSalary", model);
    }

    @PostMapping("taixe/editLuong/save")
    public String saveLuong(Model model, @ModelAttribute LuongCoBanRequest luongcoban) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addLuongCoBan",
                HttpMethod.POST, new HttpEntity<>(luongcoban, null), ResponseBuilder.class);
        model.addAttribute("luongcoban", luongcoban);
        if(responseEntity.getBody().getStatus() != 200) {
            model.addAttribute("noticeDanger", "Lỗi");
        } else {
            model.addAttribute("noticeSuccess", "Thành công!");
        }
        return "admin/taixe/editSalary";
    }

    // Thống kê
    @GetMapping("thongke/luong")
    public String homeTKLuong() {
        return "admin/thongke/salaryDriver";
    }

    @GetMapping("thongke/luong/search")
    public String searchLuong(Model model, @RequestParam("thang") Integer thang, @RequestParam("nam") Integer nam) {
        if (thang < 1 || nam < 2000 || thang > 12 || nam > new Date().getYear() + 1900) {
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
        if (errors.hasErrors()) {
            return "admin/coachs/addOrEdit";
        }
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showXeKhachByID/{bienSo}",
                ResponseBuilder.class, xeKhach.getBienSo());
        ObjectMapper objectMapper = new ObjectMapper();
        XeKhach driver = objectMapper.convertValue(builder.getData(), XeKhach.class);
        String notice = "";
        if (!ObjectUtils.isEmpty(driver)) {
            notice = "Biển số đã tồn tại!";
        } else {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addXeKhach",
                    HttpMethod.POST, new HttpEntity<>(xeKhach, null), ResponseBuilder.class);
            notice = "Thành công!";
        }
        model.addAttribute("notice", notice);
        return "admin/coachs/addOrEdit";
    }

    @GetMapping("thongke/chuyenxe")
    public String chart(Model model) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/thongke/chuyenxe", ResponseBuilder.class);
        ObjectMapper mapper = new ObjectMapper();
        List<TKChuyenXeRequest> result = mapper.convertValue(builder.getData(), new TypeReference<List<TKChuyenXeRequest>>() {});

        ResponseBuilder tkTaiXebuilder = rest.getForObject("http://localhost:8080/api/admin/thongke/taixe", ResponseBuilder.class);
        List<Object> listObject = (List<Object>) tkTaiXebuilder.getData();
        List<TaiXeDto> taiXeDtos = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < listObject.size(); i++) {
            String str = listObject.get(i).toString();
            str = str.substring(1, str.length()-1);
            String[] array = str.split(",");
            TaiXeDto taiXeDto = new TaiXeDto();
            taiXeDto.setTen(array[0].trim());
            taiXeDto.setDiaChi(array[1].trim());
            taiXeDto.setNgaySinh(StringUtils.reverse(array[2].trim()).replaceAll("-", "/"));
            if(!array[3].trim().equals("null")) {
                taiXeDto.setLaixe(Integer.parseInt(array[3].trim()));
            }
            if(!array[4].trim().equals("null")) {
                taiXeDto.setPhuxe(Integer.parseInt(array[4].trim()));
            }
            taiXeDtos.add(taiXeDto);
        }

        List<Object> list = new ArrayList<>();
        for(TKChuyenXeRequest i : result) {
            list.add(List.of(i.getDate(), i.getSoluong()));
        }

        model.addAttribute("chartData", list);
        model.addAttribute("listtaixe", taiXeDtos);
        return "admin/thongke/trip";
    }
}