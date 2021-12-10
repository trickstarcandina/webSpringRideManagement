package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.repositories.LuongCoBanRepository;
import com.example.quanlychuyenxe.services.LuongCoBanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class LuongCoBanServiceImpl implements LuongCoBanService {
    private final LuongCoBanRepository luongCoBanRepository;

    @Override
    public Response create(LuongCoBan luongCoBan){
        return ResponseBuilder.ok(luongCoBanRepository.save(luongCoBan));
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
    public Response searchByLuong(Long luong) {
        return ResponseBuilder.ok(luongCoBanRepository.findByLuongContaining(luong));
    }

    @Override
    public Response findLaterLuong(String username) {
        return ResponseBuilder.ok(luongCoBanRepository.getLuongCuoiByUsername(username));
    }

}
