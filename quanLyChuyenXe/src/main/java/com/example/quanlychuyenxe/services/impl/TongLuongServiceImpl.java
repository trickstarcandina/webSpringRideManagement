package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.BaseResponse;
import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.TongLuong;
import com.example.quanlychuyenxe.model.request.LuongTrongThangRequest;
import com.example.quanlychuyenxe.model.request.TongLuongRequest;
import com.example.quanlychuyenxe.repositories.ChuyenXeRepository;
import com.example.quanlychuyenxe.repositories.LuongCoBanRepository;
import com.example.quanlychuyenxe.repositories.TaiXeRepository;
import com.example.quanlychuyenxe.repositories.TongLuongRepository;
import com.example.quanlychuyenxe.services.TongLuongService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class TongLuongServiceImpl implements TongLuongService  {
    private final TongLuongRepository tongLuongRepository;
    private final LuongCoBanRepository luongCoBanRepository;
    private final ChuyenXeRepository chuyenXeRepository;
    private final TaiXeRepository taiXeRepository;

    @Override
    public Response saveTongLuong(TongLuongRequest tongLuongRequest) {
        try {
            ChuyenXe chuyenXe = chuyenXeRepository.findById(tongLuongRequest.getChuyenXeId()).get();
            Long luongLaiXe, luongPhuXe = 0L;
            luongLaiXe = chuyenXe.getTuyenXe().getDoDai() * chuyenXe.getTuyenXe().getDoPhucTapCuaTuyenDuong() * 125L;
            luongPhuXe = chuyenXe.getTuyenXe().getDoDai() * chuyenXe.getTuyenXe().getDoPhucTapCuaTuyenDuong() * 88L;
            tongLuongRepository.saveTongLuong(luongLaiXe, tongLuongRequest.getChuyenXeId(),
                    luongCoBanRepository.getLuongCoBanByUsername(chuyenXe.getTaiXe1().getUsername()));
            tongLuongRepository.saveTongLuong(luongPhuXe, tongLuongRequest.getChuyenXeId(),
                    luongCoBanRepository.getLuongCoBanByUsername(chuyenXe.getTaiXe2().getUsername()));
            return ResponseBuilder.ok(200, "Cập nhật thành công!");
        } catch (Exception e) {
            return ResponseBuilder.ok(100, "Lỗi lưu tổng lương!");
        }

    }

    @Override
    public Response getTongLuongByDate(String username, Integer month, Integer year) {
//        return ResponseBuilder.ok(tongLuongRepository.getTongLuongNow(username, month, year));
        LuongTrongThangRequest luongTrongThangRequest = new LuongTrongThangRequest();
        List<TongLuong> list = tongLuongRepository.getTongLuongNow(username, month, year);
        if(list.size() == 0) {
            return ResponseBuilder.ok(100, "Error");
        }
        TongLuong maxBacLuong = list.stream().max((o1, o2) -> o1.getLuongCoBan().getId() - o2.getLuongCoBan().getId()).get();
        Long tongLuong = maxBacLuong.getLuongCoBan().getLuong();
        for(TongLuong i:list) {
            tongLuong += i.getLuongHoaHong();
        }
        luongTrongThangRequest.setLuong(tongLuong);
        luongTrongThangRequest.setTaiXe(taiXeRepository.findById(username).get());
        luongTrongThangRequest.setChuyenXe(list.stream().map(i -> i.getChuyenXe()).collect(Collectors.toList()));
        return ResponseBuilder.ok(luongTrongThangRequest);
    }

    @Override
    public Response getAllTongLuongByDate(Integer thang, Integer nam) {
        List<TaiXe> listtaixe = taiXeRepository.findAll();
        List<LuongTrongThangRequest> list = new ArrayList<>();
        listtaixe.forEach(taiXe -> {
            BaseResponse response = getTongLuongByDate(taiXe.getUsername(), thang, nam).build();
            if(response.getStatus() == 200) {
                list.add((LuongTrongThangRequest) response.getData());
            }
        });
        if(list.size() == 0) {
            return ResponseBuilder.ok(100, "Error");
        }
        return ResponseBuilder.ok(list);
    }
}
