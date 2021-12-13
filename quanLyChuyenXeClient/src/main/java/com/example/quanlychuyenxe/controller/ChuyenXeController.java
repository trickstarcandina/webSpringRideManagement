package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.*;
import com.example.quanlychuyenxe.model.request.ChuyenXeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("admin/chuyenxe")
public class ChuyenXeController {

    private RestTemplate rest = new RestTemplate();
    private ChuyenXe editChuyenXe = null;
    private ChuyenXeRequest chuyenXeRequest = null;

    @ModelAttribute("chuyenxeRequest")
    public ChuyenXeRequest createChuyenXe() {
        return new ChuyenXeRequest();
    }

    @GetMapping("")
    public String homeChuyenXe() {
        return "admin/chuyenxe/search";
    }

    // Tìm kiếm - search
    @GetMapping("search")
    public String searchChuyenXe(Model model, @RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi,
                                 @RequestParam("chooseType") String chooseType) {
        UriComponentsBuilder builder;
        switch (chooseType) {
            case "all": {
                builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/searchChuyenXe")
                        .queryParam("diemDau", diemDau)
                        .queryParam("diemCuoi", diemCuoi);
                break;
            }
            case "done": {
                builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/khachhang/search")
                        .queryParam("diemDau", diemDau)
                        .queryParam("diemCuoi", diemCuoi)
                        .queryParam("status", 1);
                break;
            }
            case "none": {
                builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/chuyenxe/khachhang/search")
                        .queryParam("diemDau", diemDau)
                        .queryParam("diemCuoi", diemCuoi)
                        .queryParam("status", 0);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + chooseType);
        }
        ResponseBuilder responseBuilder = rest.getForObject(builder.build().encode().toUri(), ResponseBuilder.class);
        List<ChuyenXe> listchuyenxe = (List<ChuyenXe>) responseBuilder.getData();
        model.addAttribute("listchuyenxe", listchuyenxe);
        return "admin/chuyenxe/search";
    }

    // Xem chi tiết
    @GetMapping("detail/{id}")
    public String showKhachHang(Model model, @PathVariable("id") Integer id) {
        ResponseBuilder chuyenxeBuilder = rest.getForObject("http://localhost:8080/api/chuyenxe/allkhachhang/{id}",
                ResponseBuilder.class, id);
        ObjectMapper objectMapper = new ObjectMapper();
        ChuyenXe chuyenXe = objectMapper.convertValue(chuyenxeBuilder.getData(), ChuyenXe.class);
        Set<KhachHang> khachHangs = chuyenXe.getKhachHangList();
        model.addAttribute("listkhachhang", khachHangs);
        return "admin/chuyenxe/showDetail";
    }

    // Thêm chuyến xe - add
    @GetMapping("add")
    public String addChuyenXe(Model model) {
        editChuyenXe = new ChuyenXe();
        model.addAttribute("chuyenxe", new ChuyenXe());
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("add/searchtuyenxe")
    public String searchTuyenXe(Model model, @RequestParam("diemDau") String diemDau, @RequestParam("diemCuoi") String diemCuoi) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/tuyenxe/searchTuyenXe")
                .queryParam("diemDau", diemDau)
                .queryParam("diemCuoi", diemCuoi);
        ResponseBuilder responseBuilder = rest.getForObject(builder.build().encode().toUri(), ResponseBuilder.class);
        List<TuyenXe> listtuyenxe = (List<TuyenXe>) responseBuilder.getData();
        model.addAttribute("listtuyenxe", listtuyenxe);
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("add/selecttuyenxe/{id}")
    public String selectTuyenXe(Model model, @PathVariable("id") String id, HttpSession session) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/tuyenxe/showTuyenXeByID/{id}",
                ResponseBuilder.class, id);
        ObjectMapper objectMapper = new ObjectMapper();
        TuyenXe tuyenxe = objectMapper.convertValue(builder.getData(), TuyenXe.class);
//        session.setAttribute("tuyenxe", tuyenxe);
        editChuyenXe.setTuyenXe(tuyenxe);
        model.addAttribute("chuyenxe", editChuyenXe);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("add/removetuyenxe")
    public String removeTuyenXe(Model model, HttpSession session) {
//        session.removeAttribute("tuyenxe");
        editChuyenXe.setTuyenXe(null);
        model.addAttribute("tuyenxe", null);
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("add/searchxekhach")
    public String searchXeKhach(Model model, @RequestParam("tenxekhach") String tenxekhach) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/searchXeKhach?tenxekhach=" + tenxekhach,
                ResponseBuilder.class);
        List<XeKhach> listxekhach = (List<XeKhach>) builder.getData();
        model.addAttribute("listxekhach", listxekhach);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("add/selectxekhach/{bienSo}")
    public String selectXeKhach(Model model, @PathVariable("bienSo") String bienSo) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showXeKhachByID/{bienSo}",
                ResponseBuilder.class, bienSo);
        ObjectMapper objectMapper = new ObjectMapper();
        XeKhach xeKhach = objectMapper.convertValue(builder.getData(), XeKhach.class);
        editChuyenXe.setXeKhach(xeKhach);
        model.addAttribute("chuyenxe", editChuyenXe);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("add/removexekhach")
    public String removeXeKhach(Model model, HttpSession session) {
//        session.removeAttribute("xekhach");
        editChuyenXe.setXeKhach(null);
        model.addAttribute("xekhach", null);
//        model.addAttribute("tuyenxe", session.getAttribute("tuyenxe"));
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @PostMapping("save")
    public String saveChuyenXe(Model model, @Valid @ModelAttribute("chuyenxeRequest") ChuyenXeRequest chuyenXeRequest,
                               Errors errors, HttpSession session) {
//        TuyenXe tuyenXe = (TuyenXe) session.getAttribute("tuyenxe");
//        XeKhach xeKhach = (XeKhach) session.getAttribute("xekhach");
        if(errors.hasErrors()) {
            model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
            model.addAttribute("xekhach", editChuyenXe.getXeKhach());
            model.addAttribute("chuyenxe", editChuyenXe);
            return "admin/chuyenxe/addOrEdit";
        }
        String notice = "Thành công";
        chuyenXeRequest.setTuyen_xe_id(editChuyenXe.getTuyenXe().getId());
        chuyenXeRequest.setXe_khach_bien_so(editChuyenXe.getXeKhach().getBienSo());

//        session.removeAttribute("xekhach");
//        session.removeAttribute("tuyenxe");
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/addChuyenXe",
                HttpMethod.POST, new HttpEntity<>(chuyenXeRequest, null), ResponseBuilder.class);
        editChuyenXe = null;
        model.addAttribute("notice", notice);
        return "admin/chuyenxe/search";
    }

    // Sửa chuyến xe - update
    @GetMapping("edit/{id}")
    public String editDriver(Model model, @PathVariable("id") Integer id) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showChuyenXe/{id}",
                ResponseBuilder.class, id);
        ObjectMapper objectMapper = new ObjectMapper();
        ChuyenXe chuyenXe = objectMapper.convertValue(builder.getData(), ChuyenXe.class);
        chuyenXe.setIsEdit(true);
        editChuyenXe = chuyenXe;
        chuyenXeRequest = new ChuyenXeRequest();
        chuyenXeRequest.setGiaVe(chuyenXe.getGiaVe());
        chuyenXeRequest.setThoiGianKetThuc(chuyenXe.getThoiGianKetThuc());
        chuyenXeRequest.setThoiGianKhoiHanh(chuyenXe.getThoiGianKhoiHanh());
        model.addAttribute("chuyenxeRequest", chuyenXeRequest);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        model.addAttribute("laixe", editChuyenXe.getTaiXe1());
        model.addAttribute("phuxe", editChuyenXe.getTaiXe2());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("edit/searchlaixe")
    public String searchTaiXe(ModelMap model, @RequestParam("tentaixe") String tentaixe) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/searchTaiXe?tenTaiXe=" + tentaixe,
                ResponseBuilder.class);
        List<TaiXe> listtaixe = (List<TaiXe>) builder.getData();
        model.addAttribute("listtaixe", listtaixe);
        model.addAttribute("chuyenxeRequest", chuyenXeRequest);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        model.addAttribute("laixe", editChuyenXe.getTaiXe1());
        model.addAttribute("phuxe", editChuyenXe.getTaiXe2());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("edit/searchphuxe")
    public String searchPhuXe(ModelMap model, @RequestParam("tentaixe") String tentaixe) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/searchTaiXe?tenTaiXe=" + tentaixe,
                ResponseBuilder.class);
        List<TaiXe> listtaixe = (List<TaiXe>) builder.getData();
        model.addAttribute("listphuxe", listtaixe);
        model.addAttribute("chuyenxeRequest", chuyenXeRequest);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        model.addAttribute("laixe", editChuyenXe.getTaiXe1());
        model.addAttribute("phuxe", editChuyenXe.getTaiXe2());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("edit/selectlaixe/{username}")
    public String selectLaiXe(Model model, @PathVariable("username") String username) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showTaiXe/{username}",
                ResponseBuilder.class, username);
        ObjectMapper objectMapper = new ObjectMapper();
        TaiXe taixe = objectMapper.convertValue(builder.getData(), TaiXe.class);
        if(editChuyenXe.getTaiXe2() != null && taixe.getCmtTaiXe().equals(editChuyenXe.getTaiXe2().getCmtTaiXe())) {
            model.addAttribute("notice", "Tài xế trùng với phụ xe");
        } else {
            editChuyenXe.setTaiXe1(taixe);
        }
        model.addAttribute("chuyenxeRequest", chuyenXeRequest);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        model.addAttribute("laixe", editChuyenXe.getTaiXe1());
        model.addAttribute("phuxe", editChuyenXe.getTaiXe2());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("edit/selectphuxe/{username}")
    public String selectPhuXe(Model model, @PathVariable("username") String username) {
        ResponseBuilder builder = rest.getForObject("http://localhost:8080/api/admin/showTaiXe/{username}",
                ResponseBuilder.class, username);
        ObjectMapper objectMapper = new ObjectMapper();
        TaiXe taixe = objectMapper.convertValue(builder.getData(), TaiXe.class);
        if(editChuyenXe.getTaiXe1() != null && taixe.getCmtTaiXe().equals(editChuyenXe.getTaiXe1().getCmtTaiXe())) {
            model.addAttribute("notice", "Phụ xe trùng với tài xế");
        } else {
            editChuyenXe.setTaiXe2(taixe);
        }
        model.addAttribute("chuyenxeRequest", chuyenXeRequest);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        model.addAttribute("laixe", editChuyenXe.getTaiXe1());
        model.addAttribute("phuxe", editChuyenXe.getTaiXe2());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("edit/removelaixe")
    public String removeLaiXe(Model model) {
        editChuyenXe.setTaiXe1(null);
        model.addAttribute("chuyenxeRequest", chuyenXeRequest);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        model.addAttribute("laixe", editChuyenXe.getTaiXe1());
        model.addAttribute("phuxe", editChuyenXe.getTaiXe2());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @GetMapping("edit/removephuxe")
    public String removePhuXe(Model model) {
        editChuyenXe.setTaiXe2(null);
        model.addAttribute("chuyenxeRequest", chuyenXeRequest);
        model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
        model.addAttribute("xekhach", editChuyenXe.getXeKhach());
        model.addAttribute("laixe", editChuyenXe.getTaiXe1());
        model.addAttribute("phuxe", editChuyenXe.getTaiXe2());
        model.addAttribute("chuyenxe", editChuyenXe);
        return "admin/chuyenxe/addOrEdit";
    }

    @PostMapping("update")
    public String updateChuyenXe(Model model, @Valid @ModelAttribute("chuyenxeRequest") ChuyenXeRequest chuyenXeRequest,
                               Errors errors) {
        if(errors.hasErrors()) {
            model.addAttribute("chuyenxeRequest", chuyenXeRequest);
            model.addAttribute("tuyenxe", editChuyenXe.getTuyenXe());
            model.addAttribute("xekhach", editChuyenXe.getXeKhach());
            model.addAttribute("chuyenxe", editChuyenXe);
            return "admin/chuyenxe/addOrEdit";
        }
        String notice = "Thành công";
        chuyenXeRequest.setTuyen_xe_id(editChuyenXe.getTuyenXe().getId());
        chuyenXeRequest.setXe_khach_bien_so(editChuyenXe.getXeKhach().getBienSo());
        if(editChuyenXe.getTaiXe1() != null) {
            chuyenXeRequest.setUsernameLaiXe(editChuyenXe.getTaiXe1().getUsername());
        }
        if(editChuyenXe.getTaiXe2() != null) {
            chuyenXeRequest.setUsernamePhuXe(editChuyenXe.getTaiXe2().getUsername());
        }

        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/updateChuyenXe?id="
                 + editChuyenXe.getId(), HttpMethod.PUT, new HttpEntity<>(chuyenXeRequest, null), ResponseBuilder.class);

        editChuyenXe = null;
        model.addAttribute("notice", notice);
        return "admin/chuyenxe/search";
    }

    // Xóa chuyến xe - delete
    @GetMapping("delete/{id}")
    public ModelAndView deleteChuyenXe(ModelMap model, @PathVariable("id") Integer id) {
        ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/deleteChuyenXe/" + id,
                HttpMethod.DELETE, null, ResponseBuilder.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("deleteNotice", "Xóa thành công");
        }
        return new ModelAndView("admin/chuyenxe/search", model);
    }
}
