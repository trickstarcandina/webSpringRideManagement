package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.TongLuong;
import com.example.quanlychuyenxe.model.request.TongLuongRequest;
import com.example.quanlychuyenxe.repositories.ChuyenXeRepository;
import com.example.quanlychuyenxe.repositories.LuongCoBanRepository;
import com.example.quanlychuyenxe.repositories.TongLuongRepository;
import com.example.quanlychuyenxe.services.TongLuongService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class TongLuongServiceImpl implements TongLuongService  {
    private final TongLuongRepository tongLuongRepository;
    private final LuongCoBanRepository luongCoBanRepository;
    private final ChuyenXeRepository chuyenXeRepository;

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
        return ResponseBuilder.ok(tongLuongRepository.getTongLuongNow(username, month, year));
    }
}
