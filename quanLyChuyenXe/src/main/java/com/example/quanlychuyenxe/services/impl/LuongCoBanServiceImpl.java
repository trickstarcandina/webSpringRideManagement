package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.request.LuongCoBanRequest;
import com.example.quanlychuyenxe.repositories.LuongCoBanRepository;
import com.example.quanlychuyenxe.repositories.TaiXeRepository;
import com.example.quanlychuyenxe.services.LuongCoBanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class LuongCoBanServiceImpl implements LuongCoBanService {
    private final LuongCoBanRepository luongCoBanRepository;
    private final TaiXeRepository taiXeRepository;

    @Override
    public Response create(LuongCoBanRequest luongCoBanRequest){
        LuongCoBan luongCoBan = new LuongCoBan();
        if(luongCoBanRequest.getId() != null) luongCoBan.setId(luongCoBanRequest.getId());
        luongCoBan.setLuong(luongCoBanRequest.getLuong());
        luongCoBan.setThangLuong(luongCoBanRequest.getThangLuong());
        luongCoBan.setPhuCap(luongCoBanRequest.getPhuCap());
        luongCoBan.setGhiChu(luongCoBanRequest.getGhiChu());
        if(luongCoBanRequest.getUsername() != null) {
            TaiXe taiXe = taiXeRepository.findById(luongCoBanRequest.getUsername()).get();
            luongCoBan.setTaiXe(taiXe);
            return ResponseBuilder.ok(luongCoBanRepository.save(luongCoBan));
        }
        return ResponseBuilder.ok(100, "Error!");
    }

    @Override
    public Response delete(Integer id) {
        luongCoBanRepository.deleteById(id);
        return ResponseBuilder.ok();
    }

    @Override
    public Response searchById(Integer id) {
        return ResponseBuilder.ok(luongCoBanRepository.findById(id));
    }

    @Override
    public Response findLuongByTaiXe(String username) {
        LuongCoBan luongCoBan = luongCoBanRepository.findLuongCoBanByTaiXe_Username(username);
        LuongCoBanRequest luongCoBanRequest = new LuongCoBanRequest();
        luongCoBanRequest.setLuong(luongCoBan.getLuong());
        luongCoBanRequest.setThangLuong(luongCoBan.getThangLuong());
        luongCoBanRequest.setUsername(luongCoBan.getTaiXe().getUsername());
        luongCoBanRequest.setPhuCap(luongCoBan.getPhuCap());
        luongCoBanRequest.setGhiChu(luongCoBan.getGhiChu());
        luongCoBanRequest.setId(luongCoBan.getId());
        return ResponseBuilder.ok(luongCoBanRequest);
    }

    @Override
    public Response findLaterLuong(String username) {
        return ResponseBuilder.ok(luongCoBanRepository.getLuongCuoiByUsername(username));
    }

}
