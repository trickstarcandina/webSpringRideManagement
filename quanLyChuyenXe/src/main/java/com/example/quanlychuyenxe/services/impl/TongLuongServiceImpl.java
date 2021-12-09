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
            Long luongHoaHong = 0L;
            if(chuyenXe.getTaiXe1().getCmtTaiXe() == tongLuongRequest.getCmtTaiXeId()) {
                luongHoaHong = chuyenXe.getTuyenXe().getDoDai() * chuyenXe.getTuyenXe().getDoPhucTapCuaTuyenDuong() * 125L;
            } else {
                luongHoaHong = chuyenXe.getTuyenXe().getDoDai() * chuyenXe.getTuyenXe().getDoPhucTapCuaTuyenDuong() * 88L;
            }
            Integer idLuongCung = luongCoBanRepository.getLuongCoBanByCmt(tongLuongRequest.getCmtTaiXeId());
            tongLuongRepository.saveTongLuong(luongHoaHong, tongLuongRequest.getChuyenXeId(), idLuongCung);
            return ResponseBuilder.ok(200, "Cập nhật thành công!");
        } catch (Exception e) {
            return ResponseBuilder.ok(100, "Lỗi lưu tổng lương!");
        }

    }
}
