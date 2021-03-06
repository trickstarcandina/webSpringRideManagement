package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.*;
import com.example.quanlychuyenxe.model.request.ChuyenXeRequest;
import com.example.quanlychuyenxe.model.request.TKChuyenXeRequest;
import com.example.quanlychuyenxe.repositories.ChuyenXeRepository;
import com.example.quanlychuyenxe.repositories.TaiXeRepository;
import com.example.quanlychuyenxe.repositories.TuyenXeRepository;
import com.example.quanlychuyenxe.repositories.XeKhachRepository;
import com.example.quanlychuyenxe.services.ChuyenXeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class ChuyenXeServiceImpl implements ChuyenXeService {
    private final ChuyenXeRepository chuyenXeRepository;
    private final TuyenXeRepository tuyenXeRepository;
    private final XeKhachRepository xeKhachRepository;
    private final TaiXeRepository taiXeRepository;

    @Override
    public Response create(ChuyenXeRequest chuyenXeRequest) {
        ChuyenXe chuyenXe = new ChuyenXe();
        chuyenXe.setGiaVe(chuyenXeRequest.getGiaVe());
        chuyenXe.setThoiGianKhoiHanh(chuyenXeRequest.getThoiGianKhoiHanh());
        chuyenXe.setThoiGianKetThuc(chuyenXeRequest.getThoiGianKetThuc());
        chuyenXe.setSoLuongHanhKhach(chuyenXeRequest.getSoLuongHanhKhach());
        chuyenXe.setStatus(0);

        if(chuyenXeRequest.getUsernameLaiXe() != null) {
            TaiXe laixe = taiXeRepository.findById(chuyenXeRequest.getUsernameLaiXe()).get();
            chuyenXe.setTaiXe1(laixe);
        }
        if(chuyenXeRequest.getUsernamePhuXe() != null) {
            TaiXe phuxe = taiXeRepository.findById(chuyenXeRequest.getUsernamePhuXe()).get();
            chuyenXe.setTaiXe2(phuxe);
        }
        if(chuyenXeRequest.getTuyen_xe_id() != null) {
            TuyenXe tuyenXe = tuyenXeRepository.findById(chuyenXeRequest.getTuyen_xe_id()).get();
            chuyenXe.setTuyenXe(tuyenXe);
        }
        if(chuyenXeRequest.getXe_khach_bien_so() != null) {
            XeKhach xeKhach = xeKhachRepository.findById(chuyenXeRequest.getXe_khach_bien_so()).get();
            chuyenXe.setXeKhach(xeKhach);
        }

        if(chuyenXeRequest.getId() != null) chuyenXe.setId(chuyenXeRequest.getId());

        if(chuyenXeRequest.getKhachHangList() != null) {
            chuyenXe.setKhachHangList(chuyenXeRequest.getKhachHangList());
        }

        return ResponseBuilder.ok(chuyenXeRepository.save(chuyenXe));
    }

    @Override
    public Response delete(Integer id) {
        chuyenXeRepository.deleteById(id);
        return ResponseBuilder.ok();
    }

    @Override
    public Response searchById(Integer id) {
        return ResponseBuilder.ok(chuyenXeRepository.findById(id));
    }

    @Override
    public Response getAll() {
        return ResponseBuilder.ok(chuyenXeRepository.findAll());
    }

    @Override
    public Response searchByTuyenXe(String diemDau, String diemCuoi) {
        return ResponseBuilder.ok(chuyenXeRepository.findAllByTuyenXe_DiemDauContainingAndTuyenXe_DiemCuoiContaining(diemDau, diemCuoi));
    }


    @Override
    public Response thongkeChuyenXe() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date end = subtractDays(new Date(), 1);
        Date start = subtractDays(end, 7);
        List<TKChuyenXeRequest> result = new ArrayList<>();
        result.add(new TKChuyenXeRequest(formatter.format(start) + " - " + formatter.format(end),
                chuyenXeRepository.getChuyenXe(subtractDays(start, 1), addDays(end, 1))));
        for (int i = 0; i < 4; i++) {
            end = start;
            start = subtractDays(end, 7);
            result.add(new TKChuyenXeRequest(formatter.format(start) + " - " + formatter.format(end),
                    chuyenXeRepository.getChuyenXe(subtractDays(start, 1), addDays(end, 1))));
        }
        Collections.reverse(result);
        return ResponseBuilder.ok(result);
    }

    @Override
    public Response thongkeTaiXe() {
        return ResponseBuilder.ok(chuyenXeRepository.getListTaiXe());
    }

    private Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    private Date subtractDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    @Override
    public Response searchLaiXeByStatus(String username, Integer status) {
        return ResponseBuilder.ok(chuyenXeRepository.searchTaiXeAndStatus(status, username, username));
    }

    @Override
    public Response updateStatus(Integer id, Integer status) {
        try {
            chuyenXeRepository.updateStatusById(status, id);
            return ResponseBuilder.ok(200, "C???p nh???t th??nh c??ng!");
        } catch (Exception e) {
            return ResponseBuilder.ok(100, "L???i!!");
        }
    }

    @Override
    public Response findChuyenXeThieuTaiXe(String diemDau, String diemCuoi) {
        return ResponseBuilder.ok(chuyenXeRepository
                .findAllByTaiXe1NullOrTaiXe2NullAndTuyenXe_DiemDauContainingAndTuyenXe_DiemCuoiContaining(diemDau, diemCuoi));
    }

    @Override
    public Response updateLaiXeById(Integer id, String username) {
        try {
            chuyenXeRepository.updateLaiXeById(username, id);
            return ResponseBuilder.ok(200, "????ng k?? th??nh c??ng!");
        } catch (Exception e) {
            return ResponseBuilder.ok(100, "L???i!!");
        }
    }

    @Override
    public Response updatePhuXeById(Integer id, String username) {
        try {
            chuyenXeRepository.updatePhuXeById(username, id);
            return ResponseBuilder.ok(200, "????ng k?? th??nh c??ng!");
        } catch (Exception e) {
            return ResponseBuilder.ok(100, "L???i!!");
        }
    }

    @Override
    public Response findChuyenXeByStatus(String diemDau, String diemCuoi, Integer status) {
        return ResponseBuilder.ok(chuyenXeRepository.findAllByTuyenXe_DiemDauContainingAndTuyenXe_DiemCuoiContainingAndStatus(diemDau, diemCuoi, status));
    }

    @Override
    public Response update(ChuyenXe chuyenXe) {
        return ResponseBuilder.ok(chuyenXeRepository.save(chuyenXe));
    }

    @Override
    public Response allKhachHang(Integer id) {
        ChuyenXe chuyenXe = chuyenXeRepository.findById(id).get();
        Set<KhachHang> khachHangList = (Set<KhachHang>) chuyenXe.getKhachHangList();
        chuyenXe.setKhachHangList(khachHangList);
        return ResponseBuilder.ok(chuyenXe);
    }

    @Override
    public Response updateKhachHang(String username, Integer id) {
        try {
            chuyenXeRepository.saveKhachHangChuyenXe(username, id);
            return ResponseBuilder.ok(200, "Success");
        } catch (Exception e) {
            return ResponseBuilder.ok(100, "Error");
        }
    }

    @Override
    public Response updateChuyenXe(ChuyenXeRequest chuyenXeRequest) {
        try {
            chuyenXeRepository.updateChuyenXe(chuyenXeRequest.getGiaVe(), chuyenXeRequest.getUsernameLaiXe(),
                    chuyenXeRequest.getUsernamePhuXe(), chuyenXeRequest.getTuyen_xe_id(),
                    chuyenXeRequest.getXe_khach_bien_so(), chuyenXeRequest.getId());
            return ResponseBuilder.ok(200, "Success");
        } catch (Exception e) {
            return ResponseBuilder.ok(200, "Success");
        }

    }
}
